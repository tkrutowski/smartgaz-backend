package net.focik.Smartgaz.dobranocka.rent.infrastructure.dto;

import jakarta.persistence.*;
import lombok.*;
import net.focik.Smartgaz.dobranocka.rent.domain.BedStatus;
import net.focik.Smartgaz.dobranocka.rent.domain.BedType;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dobranocka_bed")
public class BedDbDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private BedType type;
    @Enumerated(EnumType.STRING)
    private BedStatus status;
    private BigDecimal priceDay;//netto
    private BigDecimal priceMonth;//netto

    @ManyToOne
    @JoinColumn(name = "id_room", nullable = false) // Klucz obcy
    private RoomDbDto room;
}
