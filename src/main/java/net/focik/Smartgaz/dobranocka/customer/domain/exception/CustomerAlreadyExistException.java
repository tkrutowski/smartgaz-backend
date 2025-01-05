package net.focik.Smartgaz.dobranocka.customer.domain.exception;


import net.focik.Smartgaz.utils.exceptions.ObjectAlreadyExistException;

public class CustomerAlreadyExistException extends ObjectAlreadyExistException {
    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
