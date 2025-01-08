package net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto;

import lombok.*;

import jakarta.persistence.*;
import net.focik.Smartgaz.utils.share.PaymentMethod;
import net.focik.Smartgaz.utils.share.PaymentStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dobranocka_invoice")
public class InvoiceDbDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idInvoice;
    private Integer idCustomer;
    private String number;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sellDate;//data sprzedaży
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate;//data faktury
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;//termin zapłaty
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String otherInfo;
}
