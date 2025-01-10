package net.focik.Smartgaz.dobranocka.rent.api.dto;

import lombok.*;
import net.focik.Smartgaz.dobranocka.rent.domain.BedType;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoomDto {
    private int id;
    private String name;
    private String color;
    private BigDecimal price;
    private String info;
    private List<BedType> beds;
}