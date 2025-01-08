package net.focik.Smartgaz.dobranocka.invoice.domain.exception;


import net.focik.Smartgaz.utils.exceptions.ObjectAlreadyExistException;

public class InvoiceAlreadyExistException extends ObjectAlreadyExistException {
    public InvoiceAlreadyExistException(String message) {
        super(message);
    }
}
