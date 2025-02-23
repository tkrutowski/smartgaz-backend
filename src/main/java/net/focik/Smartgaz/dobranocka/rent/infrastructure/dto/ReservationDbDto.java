package net.focik.Smartgaz.dobranocka.rent.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import net.focik.Smartgaz.dobranocka.customer.infrastructure.dto.CustomerDbDto;
import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceDbDto;
import net.focik.Smartgaz.dobranocka.rent.domain.ReservationStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dobranocka_reservation")
public class ReservationDbDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerDbDto customer;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    private InvoiceDbDto invoice;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationBedDbDto> beds;
    private BigDecimal advance;
    private BigDecimal deposit;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus reservationStatus;
    private String info;
}
