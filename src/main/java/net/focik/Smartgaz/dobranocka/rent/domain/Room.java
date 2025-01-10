package net.focik.Smartgaz.dobranocka.rent.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Room {
    private int id;
    private String name;
    private String color;
    private BigDecimal price;
    private String info;
    private List<BedType> bedType;
}