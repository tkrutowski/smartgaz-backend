package net.focik.Smartgaz.dobranocka.invoice.domain.invoice;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.port.primary.*;
import net.focik.Smartgaz.dobranocka.rent.domain.RentFacade;
import net.focik.Smartgaz.dobranocka.settings.domain.CompanyFacade;
import net.focik.Smartgaz.dobranocka.settings.domain.company.Company;
import net.focik.Smartgaz.utils.share.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class InvoiceFacade implements UpdateInvoiceUseCase, DeleteInvoiceUseCase, AddInvoiceUseCase, GetInvoiceUseCase, PrintInvoiceUseCase {

    private final InvoiceService invoiceService;
    private final CompanyFacade companyFacade;
    private final RentFacade rentFacade;

    @Transactional
    public Invoice addInvoice(Invoice invoice) {
        Invoice savedInvoice = invoiceService.saveInvoice(invoice);
        rentFacade.updateInvoiceInReservation(savedInvoice.getIdInvoice(), invoice.getReservationIds());
        return savedInvoice;
    }

    public Invoice findById(Integer id) {
        return invoiceService.findById(id);
    }

    public int getNewInvoiceNumber(int year) {
        return invoiceService.getNewInvoiceNumber(year);
    }

    @Override
    public List<Invoice> findAllByStatus(PaymentStatus status) {
        return invoiceService.findByAll(status);
    }

    @Override
    public List<Invoice> findAllByCustomer(Customer customer) {
        return invoiceService.findByCustomer(customer);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        return invoiceService.updateInvoice(invoice);
    }

    @Override
    public void updatePaymentStatus(Integer id, PaymentStatus paymentStatus) {
        invoiceService.updatePaymentStatus(id, paymentStatus);
    }

    @Override
    @Transactional
    public void deleteInvoice(Integer idInvoice) {
        Invoice invoice = findById(idInvoice);
        if (Objects.nonNull(invoice.getReservationIds())) {
            rentFacade.updateInvoiceInReservation(0, invoice.getReservationIds());
        }
        invoiceService.deleteInvoice(idInvoice);
    }

    @Override
    public String printInvoice(Invoice invoice) {
        Company company = companyFacade.get();
        return InvoicePdf.createPdf(invoice, company);
    }

}
