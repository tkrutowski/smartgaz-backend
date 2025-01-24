package net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto;

import lombok.*;
import net.focik.Smartgaz.utils.share.Vat;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dobranocka_invoice_item")
public class InvoiceItemDbDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String pkwiu;
    private String unit;
    private Float quantity;
    private BigDecimal amount;//netto
    @Column(name = "vat_type")
    @Enumerated(EnumType.STRING)
    private Vat vat;

    @ManyToOne
    @JoinColumn(name = "id_invoice", nullable = false) // Klucz obcy
    private InvoiceDbDto invoice;
}
