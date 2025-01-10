package net.focik.Smartgaz.dobranocka.rent.domain.exception;


import net.focik.Smartgaz.utils.exceptions.ObjectNotFoundException;

public class RoomNotFoundException extends ObjectNotFoundException {
    public RoomNotFoundException(String field, String message) {
        super(String.format("Nie znaleziono pokoju o %s: %s",field, message));
    }

}
