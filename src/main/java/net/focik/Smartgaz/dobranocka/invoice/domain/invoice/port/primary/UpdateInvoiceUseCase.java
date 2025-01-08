package net.focik.Smartgaz.dobranocka.invoice.domain.invoice.port.primary;


import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;
import net.focik.Smartgaz.utils.share.PaymentStatus;

public interface UpdateInvoiceUseCase {
    Invoice updateInvoice(Invoice invoice);

    void updatePaymentStatus(Integer id, PaymentStatus paymentStatus);
}
