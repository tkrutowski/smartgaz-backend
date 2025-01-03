package net.focik.Smartgaz.utils;

import net.focik.Smartgaz.userservice.domain.AppUser;
import net.focik.Smartgaz.userservice.domain.exceptions.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserHelper {
    private UserHelper() {
    }

    public static String getUserName() {

        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public static AppUser getUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .filter(o -> o instanceof AppUser)
                .map(AppUser.class::cast)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
