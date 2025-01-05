package net.focik.Smartgaz.dobranocka.customer.infrastructure.dto;

import lombok.*;

import jakarta.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class CustomerDbDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String name;
    private String firstName;
    private String street;
    private String zip;
    private String city;
    private String nip;
    private String regon;
    private String phone;
    private String mail;
    private String info;
}
