package net.focik.Smartgaz.dobranocka.invoice.domain.exception;


import net.focik.Smartgaz.utils.exceptions.ObjectNotFoundException;

public class InvoiceNotFoundException extends ObjectNotFoundException {
    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
