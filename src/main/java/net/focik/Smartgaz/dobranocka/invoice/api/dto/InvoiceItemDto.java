package net.focik.Smartgaz.dobranocka.invoice.api.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InvoiceItemDto {
    private long id;
    private int idInvoice;
    private String name;
    private String pkwiu;
    private String jm;
    private Number quantity;
    private Number amount;//netto
    private Number amountSum;//netto
    private VatDto vat;
}
