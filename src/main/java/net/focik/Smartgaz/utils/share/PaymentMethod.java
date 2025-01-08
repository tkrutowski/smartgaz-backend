package net.focik.Smartgaz.utils.share;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CASH("gotówka"),
    CASH_LATE("gotówka terminowa"),
    TRANSFER("przelew");

    private final String viewValue;

    PaymentMethod(String viewValue) {
        this.viewValue = viewValue;
    }

}
