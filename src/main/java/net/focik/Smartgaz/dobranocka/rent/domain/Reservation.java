package net.focik.Smartgaz.dobranocka.rent.domain;

import lombok.*;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reservation {
    private int id;
    private Customer customer;
    private List<ReservationBed> beds;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal advance;
    private BigDecimal deposit;
    private ReservationStatus reservationStatus;
    private String info;
}