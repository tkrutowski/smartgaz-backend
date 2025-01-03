package net.focik.Smartgaz.userservice.domain.exceptions;

public class PrivilegeNotFoundException extends RuntimeException {
    public PrivilegeNotFoundException(String message) {
        super(message);
    }
}
