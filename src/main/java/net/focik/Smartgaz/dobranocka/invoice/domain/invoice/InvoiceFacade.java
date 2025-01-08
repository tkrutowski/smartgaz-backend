package net.focik.Smartgaz.dobranocka.invoice.domain.invoice;

import lombok.AllArgsConstructor;
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
        int idInvoice = invoiceService.saveInvoice(invoice).getIdInvoice();
        return  findFullById(idInvoice);
    }

    public Invoice findById(Integer id) {
        return invoiceService.findById(id);
    }

    public Invoice findFullById(Integer id) {
        return invoiceService.findFullById(id);
    }

    public int getNewInvoiceNumber(int year) {
        return invoiceService.getNewInvoiceNumber(year);
    }

    public List<Invoice> findAllBy(PaymentStatus status, Boolean getItems, Boolean getCustomer) {
        return invoiceService.findByAll(status, getItems, getCustomer);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        int idInvoice = invoiceService.updateInvoice(invoice).getIdInvoice();
        return findFullById(idInvoice);
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
