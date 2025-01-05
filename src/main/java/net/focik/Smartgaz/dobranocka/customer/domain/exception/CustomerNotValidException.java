package net.focik.Smartgaz.dobranocka.customer.domain.exception;


import net.focik.Smartgaz.utils.exceptions.ObjectNotValidException;

public class CustomerNotValidException extends ObjectNotValidException {
    public CustomerNotValidException(String message) {
        super(message);
    }
}
