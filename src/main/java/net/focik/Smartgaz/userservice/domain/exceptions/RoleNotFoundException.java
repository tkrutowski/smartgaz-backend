package net.focik.Smartgaz.userservice.domain.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
