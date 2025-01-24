package net.focik.Smartgaz.dobranocka.rent.infrastructure.dto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dobranocka_reservation_bed")
public class ReservationBedDbDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false) // Klucz obcy do ReservationDbDto
    private ReservationDbDto reservation;
    @ManyToOne
    @JoinColumn(name = "bed_id", nullable = false) // Klucz obcy do BedDbDto
    private BedDbDto bed;
    private BigDecimal priceDay;
    private BigDecimal priceMonth;
}
