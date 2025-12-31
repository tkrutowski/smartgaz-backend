package net.focik.Smartgaz.dobranocka.invoice.domain.invoice.port.primary;


import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;

public interface PrintInvoiceUseCase {
    String createInvoicePdf(Invoice invoice);
    String sendInvoiceToS3(int invoice);
}
