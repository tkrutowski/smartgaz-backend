package net.focik.Smartgaz.dobranocka.invoice.domain.invoice;

import jakarta.persistence.Transient;
import lombok.*;
import net.focik.Smartgaz.utils.share.Vat;
import org.javamoney.moneta.Money;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InvoiceItem {
    private long id;
    private int idInvoice;
    private String name;
    private String pkwiu;
    private String unit;
    private float quantity;
    private double amount;//netto
    private Vat vat;

    @Transient
    public Money getAmountSumNet() {
        Money result = Money.of(amount, "PLN");
        return result.multiply(quantity);
    }

    @Transient
    public Money getAmountSumVat() {
        return  getAmountSumGross().subtract(getAmountSumNet());
    }

    @Transient
    public Money getAmountSumGross() {
        Money result = Money.of(amount, "PLN");
        return result.multiply(quantity).multiply(vat.getMultiplier());
    }
}
