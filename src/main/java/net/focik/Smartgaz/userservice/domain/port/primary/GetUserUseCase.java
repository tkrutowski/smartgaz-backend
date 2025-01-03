package net.focik.Smartgaz.userservice.domain.port.primary;


import net.focik.Smartgaz.userservice.domain.AppUser;

import java.util.List;

public interface GetUserUseCase {
    AppUser findUserByUsername(String username);

    AppUser findUserById(Long id);

    List<AppUser> getAllUsers();

}
