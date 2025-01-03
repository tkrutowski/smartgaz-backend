package net.focik.Smartgaz.userservice.domain.port.primary;


import net.focik.Smartgaz.userservice.domain.AppUser;

public interface IAddNewUserUseCase {

    AppUser addNewUser(String firstName, String lastName, String username, String password, String email, boolean enabled,
                       boolean isNotLocked);
}
