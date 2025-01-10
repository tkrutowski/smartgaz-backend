package net.focik.Smartgaz.dobranocka.customer.domain;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    private int id;
    private String name;
    private String firstName;
    private String city;
    private String street;
    private String zip;
    private String nip;
    private String phone;
    private String mail;
    private String regon;
    private String info;

    public String getAddress() {
        return String.format("%s, %s %s",getStreet(), getZip(), getCity());
    }
}