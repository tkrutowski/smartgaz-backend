package net.focik.Smartgaz.utils.share;

public enum PaymentStatus {
    PAID("Spłacony"),
    TO_PAY("Do zapłaty"),
    OVER_DUE("Przeterminowany"),
    ALL("Wszystkie");

    private final String viewValue;

    PaymentStatus(String viewValue) {
        this.viewValue = viewValue;
    }

    public String getViewValue() {
        return viewValue;
    }
}
