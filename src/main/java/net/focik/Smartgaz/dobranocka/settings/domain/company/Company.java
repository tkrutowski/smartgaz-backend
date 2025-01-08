package net.focik.Smartgaz.dobranocka.settings.domain.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Company {
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

    @JsonIgnore
    public String getAddress() {
        return String.format("%s, %s %s",getStreet(), getZip(), getCity());
    }
}