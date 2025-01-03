package net.focik.Smartgaz.utils.share;

public enum PaymentMethod {
    CASH("gotówka"),
    CASH_LATE("gotówka terminowa"),
    TRANSFER("przelew");

    private final String viewValue;

    PaymentMethod(String viewValue) {
        this.viewValue = viewValue;
    }

    public String getViewValue() {
        return viewValue;
    }
}
