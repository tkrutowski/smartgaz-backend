package net.focik.Smartgaz.utils.share;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PAID("Spłacony"),
    TO_PAY("Do zapłaty"),
    OVER_DUE("Przeterminowany"),
    ALL("Wszystkie");

    private final String viewValue;

    PaymentStatus(String viewValue) {
        this.viewValue = viewValue;
    }

}
