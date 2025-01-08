package net.focik.Smartgaz.dobranocka.invoice.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.Smartgaz.dobranocka.invoice.api.dto.InvoiceDto;
import net.focik.Smartgaz.dobranocka.invoice.api.dto.PaymentMethodDto;
import net.focik.Smartgaz.dobranocka.invoice.api.dto.VatDto;
import net.focik.Smartgaz.dobranocka.invoice.api.mapper.ApiInvoiceMapper;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.InvoicePdf;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.port.primary.*;
import net.focik.Smartgaz.utils.exceptions.ExceptionHandling;
import net.focik.Smartgaz.utils.exceptions.HttpResponse;
import net.focik.Smartgaz.utils.share.PaymentMethod;
import net.focik.Smartgaz.utils.share.PaymentStatus;
import net.focik.Smartgaz.utils.share.Vat;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dobranocka/invoice")
//@CrossOrigin
public class InvoiceController extends ExceptionHandling {

    private final GetInvoiceUseCase getInvoiceUseCase;
    private final AddInvoiceUseCase addInvoiceUseCase;
    private final UpdateInvoiceUseCase updateInvoiceUseCase;
    private final DeleteInvoiceUseCase deleteInvoiceUseCase;
    private final PrintInvoiceUseCase printInvoiceUseCase;
    private final ApiInvoiceMapper mapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_INVOICE_READ_ALL','DOBRANOCKA_INVOICE_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<InvoiceDto> getById(@PathVariable int id) {
        log.info("Request to get invoice with id: {}", id);
        Invoice invoice = getInvoiceUseCase.findById(id);

        if (invoice == null) {
            log.warn("Invoice with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Invoice with id {} found: {}", id, invoice);
        return new ResponseEntity<>(mapper.toDto(invoice), HttpStatus.OK);
    }

    @GetMapping("/pdf/{id}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_INVOICE_READ_ALL','DOBRANOCKA_INVOICE_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<?> getPdfById(@PathVariable int id) {
        log.info("Request to generate PDF for invoice with id: {}", id);
        Invoice invoice = getInvoiceUseCase.findFullById(id);

        if (invoice == null) {
            log.warn("Invoice with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("Invoice with id {} found, proceeding to generate PDF", id);
        String fileName = printInvoiceUseCase.printInvoice(invoice);
        Resource resource;
        try {
            assert fileName != null;
            Path path = Path.of(fileName);
            resource = new UrlResource(path.toUri());
            log.info("PDF generated successfully for invoice with id: {} at location: {}", id, fileName);
        } catch (IOException e) {
            log.error("Error occurred while generating PDF for invoice with id {}: {}", id, e.getMessage());
            return response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @GetMapping("/number/{year}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_INVOICE_READ_ALL','DOBRANOCKA_INVOICE_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<Integer> getInvoiceNumber(@PathVariable int year) {
        log.info("Request to get new invoice number for the year: {}", year);
        int newInvoiceNumber = getInvoiceUseCase.getNewInvoiceNumber(year);
        log.info("Generated new invoice number: {} for the year: {}", newInvoiceNumber, year);

        return new ResponseEntity<>(newInvoiceNumber, HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_INVOICE_READ_ALL','DOBRANOCKA_INVOICE_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<List<InvoiceDto>> getAllInvoices(@RequestParam PaymentStatus status) {
        log.info("Request to find all invoices with PaymentStatus: {}", status);
        List<Invoice> invoices;
        invoices = getInvoiceUseCase.findAllBy(status, true, false);
        log.info("Found {} invoices with PaymentStatus: {}", invoices.size(), status);

        return new ResponseEntity<>(invoices.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_INVOICE_WRITE_ALL','DOBRANOCKA_INVOICE_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<InvoiceDto> addInvoice(@RequestBody InvoiceDto invoiceDto) {
        log.info("Request to add a new invoice received.");
        Invoice invoice = mapper.toDomain(invoiceDto);
        log.debug("Mapped InvoiceDto to domain object: {}", invoice);

        Invoice result = addInvoiceUseCase.addInvoice(invoice);

        if (result != null && result.getIdInvoice() > 0) {
            log.info("Invoice added successfully with id = {}", result.getIdInvoice());
        } else {
            log.warn("Invoice addition failed.");
        }

        assert result != null;
        return new ResponseEntity<>(mapper.toDto(result), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_INVOICE_WRITE_ALL','DOBRANOCKA_INVOICE_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<InvoiceDto> updateInvoice(@RequestBody InvoiceDto invoiceDto) {
        log.info("Request to update invoice received.");

        Invoice invoiceToUpdate = mapper.toDomain(invoiceDto);
        log.debug("Mapped InvoiceDto to domain object: {}", invoiceToUpdate);

        Invoice updatedInvoice = updateInvoiceUseCase.updateInvoice(invoiceToUpdate);

        if (updatedInvoice != null) {
            log.info("Invoice with id {} updated successfully.", updatedInvoice.getIdInvoice());
        } else {
            log.warn("Invoice update failed.");
        }

        assert updatedInvoice != null;
        return new ResponseEntity<>(mapper.toDto(updatedInvoice), HttpStatus.OK);
    }

    @DeleteMapping("/{idInvoice}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_INVOICE_DELETE_ALL','DOBRANOCKA_INVOICE_DELETE') or hasRole('ROLE_ADMIN')")

    public ResponseEntity<HttpResponse> deleteInvoice(@PathVariable int idInvoice) {
        log.info("Request to delete invoice with id: {}", idInvoice);
        deleteInvoiceUseCase.deleteInvoice(idInvoice);
        log.info("Invoice with id {} deleted successfully.", idInvoice);
        return response(HttpStatus.NO_CONTENT, "Faktura usunięta.");
    }

    @PutMapping("/paymentstatus/{id}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_INVOICE_WRITE_ALL','DOBRANOCKA_INVOICE_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> updatePaymentStatus(@PathVariable int id, @RequestParam PaymentStatus status) {
        log.info("Request to update payment status for invoice with id: {}, new status: {}", id, status);
        updateInvoiceUseCase.updatePaymentStatus(id, status);
        log.info("Payment status updated successfully for invoice with id: {} to status: {}", id, status);
        return response(HttpStatus.OK, "Zaaktualizowano status pracownika.");
    }

    @GetMapping("/paymenttype")
    ResponseEntity<List<PaymentMethodDto>> getPaymentTypes() {
        log.info("Request to get all payment types.");
        PaymentMethod[] collect = (PaymentMethod.values());
        List<PaymentMethodDto> paymentTypeDtos = Arrays.stream(collect)
                .map(type -> new PaymentMethodDto(type.name(), type.getViewValue()))
                .collect(Collectors.toList());
        log.info("Found {} payment types.", paymentTypeDtos.size());
        return new ResponseEntity<>(paymentTypeDtos, OK);
    }

    @GetMapping("/vattype")
    ResponseEntity<List<VatDto>> getVatTypes() {
        log.info("Request to get all vat types.");
        Vat[] collect = (Vat.values());
        List<VatDto> vatTypeDtos = Arrays.stream(collect)
                .map(type -> new VatDto(type.getViewValue(), type.getNumberValue(), type.getMultiplier()))
                .collect(Collectors.toList());
        log.info("Found {} vat types.", vatTypeDtos.size());
        return new ResponseEntity<>(vatTypeDtos, OK);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus status, String message) {
        HttpResponse body = new HttpResponse(status.value(), status, status.getReasonPhrase(), message);
        return new ResponseEntity<>(body, status);
    }
}