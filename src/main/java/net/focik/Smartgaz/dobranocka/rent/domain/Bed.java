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
public class Bed {
    private int id;
    private String name;
    private BedType bedType;
    private BedStatus bedStatus;
    private BigDecimal priceDay;
    private BigDecimal priceMonth;
}