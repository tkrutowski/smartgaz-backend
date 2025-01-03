package net.focik.Smartgaz.utils.exceptions;

//@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ObjectCanNotBeDeletedException extends RuntimeException {
    public ObjectCanNotBeDeletedException(String message) {
        super(message);
    }
}
