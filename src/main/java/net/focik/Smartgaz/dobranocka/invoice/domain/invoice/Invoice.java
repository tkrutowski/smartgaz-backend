package net.focik.Smartgaz.dobranocka.invoice.domain.invoice;

import jakarta.persistence.Transient;
import lombok.*;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.invoice.domain.exception.InvoiceItemNotFoundException;
import net.focik.Smartgaz.utils.share.PaymentMethod;
import net.focik.Smartgaz.utils.share.PaymentStatus;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Invoice {
    private int idInvoice;
    private String invoiceNumber;
    private PaymentMethod paymentMethod;
    private LocalDate sellDate;//data sprzedaży
    private LocalDate invoiceDate;//data faktury
    private PaymentStatus paymentStatus;
    private LocalDate paymentDate;//termin zapłaty
    private String otherInfo;
    private List<InvoiceItem> invoiceItems;
    private Customer customer;

    @Transient
    public void changePaymentStatus(PaymentStatus newPaymentStatus) {
        this.paymentStatus = newPaymentStatus;
    }

    @Transient
    public Money getAmountVat() {
        Optional<Money> reduce = invoiceItems.stream()
                .map(InvoiceItem::getAmountSumVat)
                .reduce(Money::add);
        return reduce.orElseThrow(() -> new InvoiceItemNotFoundException("Error during getAmountVat"));
    }

    @Transient
    public Money getAmountNet() {
        Optional<Money> reduce = invoiceItems.stream()
                .map(InvoiceItem::getAmountSumNet)
                .reduce(Money::add);
        return reduce.orElseThrow(() -> new InvoiceItemNotFoundException("Error during getAmountNet"));
    }

    @Transient
    public Money getAmountGross() {
        Optional<Money> reduce = invoiceItems.stream()
                .map(InvoiceItem::getAmountSumGross)
                .reduce(Money::add);
        return reduce.orElseThrow(() -> new InvoiceItemNotFoundException("Error during getAmountGross"));
    }
}
