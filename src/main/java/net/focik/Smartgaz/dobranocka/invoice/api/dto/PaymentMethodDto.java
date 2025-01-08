package net.focik.Smartgaz.dobranocka.invoice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentMethodDto {
    private String name;
    private String viewName;
}
