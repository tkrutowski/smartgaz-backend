package net.focik.Smartgaz.dobranocka.invoice.domain.exception;


import net.focik.Smartgaz.utils.exceptions.ObjectNotValidException;

public class InvoiceNotValidException extends ObjectNotValidException {
    public InvoiceNotValidException(String message) {
        super(message);
    }
}
