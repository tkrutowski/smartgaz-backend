package net.focik.Smartgaz.dobranocka.rent.domain.exception;


import net.focik.Smartgaz.utils.exceptions.ObjectAlreadyExistException;

public class RoomAlreadyExistException extends ObjectAlreadyExistException {
    public RoomAlreadyExistException(String message) {
        super(message);
    }
}
