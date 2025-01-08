package net.focik.Smartgaz.dobranocka.invoice.domain.invoice.port.secondary;

import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.InvoiceItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface InvoiceRepository {

    Invoice save(Invoice invoice);

    void deleteInvoice(Integer id);

    List<Invoice> findAll();

    Optional<Invoice> findById(Integer id);

    Optional<Invoice> findByNumber(String number);

    List<InvoiceItem> findByInvoiceId(Integer idInvoice);

    void deleteAllInvoiceItemsByInvoiceId(Integer id);
}
