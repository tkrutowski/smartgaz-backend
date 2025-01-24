package net.focik.Smartgaz.dobranocka.invoice.domain.invoice;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.port.primary.*;
import net.focik.Smartgaz.dobranocka.settings.domain.CompanyFacade;
import net.focik.Smartgaz.dobranocka.settings.domain.company.Company;
import net.focik.Smartgaz.utils.share.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class InvoiceFacade implements UpdateInvoiceUseCase, DeleteInvoiceUseCase, AddInvoiceUseCase, GetInvoiceUseCase, PrintInvoiceUseCase {

    private final InvoiceService invoiceService;
    private final CompanyFacade companyFacade;

    public Invoice addInvoice(Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
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
    public void deleteInvoice(Integer idInvoice) {
        invoiceService.deleteInvoice(idInvoice);
    }

    @Override
    public String printInvoice(Invoice invoice) {
        Company company = companyFacade.get();
        return InvoicePdf.createPdf(invoice, company);
    }

}
