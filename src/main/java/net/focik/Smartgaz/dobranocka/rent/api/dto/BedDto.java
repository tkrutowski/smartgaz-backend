package net.focik.Smartgaz.dobranocka.rent.api.dto;

import lombok.*;
import net.focik.Smartgaz.dobranocka.rent.domain.BedStatus;
import net.focik.Smartgaz.dobranocka.rent.domain.BedType;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BedDto {
    private int id;
    private String name;
    private BedType type;
    private BedStatus status;
    private BigDecimal priceDay;
    private BigDecimal priceMonth;
}