package net.focik.Smartgaz.dobranocka.invoice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VatDto {
    private  String viewValue;
    private  int numberValue;
    private  double multiplier;

    @Override
    public String toString() {
        return "VAT_" + numberValue;
    }
}
