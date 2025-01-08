package net.focik.Smartgaz.dobranocka.invoice.domain.exception;


import net.focik.Smartgaz.utils.exceptions.ObjectNotFoundException;

public class InvoiceItemNotFoundException extends ObjectNotFoundException {
    public InvoiceItemNotFoundException(String message) {
        super(message);
    }
}
