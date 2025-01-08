package net.focik.Smartgaz.dobranocka.invoice.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InvoiceDto {
    private int idInvoice;
    private int idCustomer;
    private String invoiceNumber;
    private Number amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Warsaw")
    private LocalDate sellDate;//data sprzedaży
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Warsaw")
    private LocalDate invoiceDate;//data faktury
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Warsaw")
    private LocalDate paymentDate;//termin zapłaty
    private int paymentDeadline;
    private PaymentStatusDto paymentStatus;
    private PaymentMethodDto paymentMethod;
    private String paymentTypeView;
    private String otherInfo;
    private String customerName;
    private List<InvoiceItemDto> invoiceItems;

}
