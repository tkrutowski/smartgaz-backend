package net.focik.Smartgaz.dobranocka.rent.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationBedDto {
    private Integer id;
    private BedDto bed;
    private BigDecimal priceDay;
    private BigDecimal priceMonth;
}
