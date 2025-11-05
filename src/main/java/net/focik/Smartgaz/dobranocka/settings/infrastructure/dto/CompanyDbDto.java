package net.focik.Smartgaz.dobranocka.settings.infrastructure.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "company")
public class CompanyDbDto {
    @Id
    private String name;
    private String city;
    private String street;
    private String zip;
    private String nip;
    private String regon;
    private String phone1;
    private String phone2;
    private String fax;
    private String mail;
    private String www;
    private String bank;
    private String accountNo;
    private String info;

}