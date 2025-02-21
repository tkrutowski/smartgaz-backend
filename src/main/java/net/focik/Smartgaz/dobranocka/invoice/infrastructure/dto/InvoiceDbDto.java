package net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import net.focik.Smartgaz.dobranocka.customer.infrastructure.dto.CustomerDbDto;
import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.ReservationDbDto;
import net.focik.Smartgaz.utils.share.PaymentMethod;
import net.focik.Smartgaz.utils.share.PaymentStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerDbDto customer;
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

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItemDbDto> invoiceItems;
//
    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ReservationDbDto> reservations;
}
