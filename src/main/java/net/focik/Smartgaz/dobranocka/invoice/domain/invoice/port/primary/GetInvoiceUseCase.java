package net.focik.Smartgaz.dobranocka.invoice.domain.invoice.port.primary;


import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;
import net.focik.Smartgaz.utils.share.PaymentStatus;

import java.util.List;

public interface GetInvoiceUseCase {
    Invoice findById(Integer id);

//    Invoice findFullById(Integer idInvoice);

    int getNewInvoiceNumber(int year);

    List<Invoice> findAllByStatus(PaymentStatus status);

    List<Invoice> findAllByCustomer(Customer customer);
}
