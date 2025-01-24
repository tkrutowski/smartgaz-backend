package net.focik.Smartgaz.dobranocka.invoice.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import net.focik.Smartgaz.dobranocka.customer.api.dto.CustomerDto;
import net.focik.Smartgaz.utils.share.PaymentMethod;
import net.focik.Smartgaz.utils.share.PaymentStatus;

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
    private String invoiceNumber;
    private PaymentMethod paymentMethod;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Warsaw")
    private LocalDate sellDate;//data sprzedaży
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Warsaw")
    private LocalDate invoiceDate;//data faktury
    private PaymentStatus paymentStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Warsaw")
    private LocalDate paymentDate;//termin zapłaty
    private String otherInfo;
    private List<InvoiceItemDto> invoiceItems;
    private CustomerDto customer;

}
