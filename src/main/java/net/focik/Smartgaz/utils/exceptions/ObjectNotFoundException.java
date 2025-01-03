package net.focik.Smartgaz.utils.exceptions;

//@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
