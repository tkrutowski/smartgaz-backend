package net.focik.Smartgaz.dobranocka.invoice.domain.invoice;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import lombok.*;
import net.focik.Smartgaz.utils.share.Vat;
import org.javamoney.moneta.Money;

import static net.focik.Smartgaz.utils.prints.FontUtil.FONT_10;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InvoiceItem {
    private int idInvoice;
    private long idInvoiceItem;
    private String name;
    private String pkwiu;
    private String unit;
    private float quantity;
    private Money amount;//netto
    private Vat vat;

    public Money getAmountSumNet() {
        return amount.multiply(quantity);
    }

    public Money getAmountSumVat() {
        return  getAmountSumGross().subtract(getAmountSumNet());
    }

    public Money getAmountSumGross() {
        return amount.multiply(quantity).multiply(vat.getMultiplier());
    }
}
