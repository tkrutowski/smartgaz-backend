package net.focik.Smartgaz.dobranocka.rent.domain;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReservationBed {
    private int id;
    private Bed bed;
    private BigDecimal priceDay;
    private BigDecimal priceMonth;
}