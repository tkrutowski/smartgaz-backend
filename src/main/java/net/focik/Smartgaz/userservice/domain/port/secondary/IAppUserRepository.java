package net.focik.Smartgaz.userservice.domain.port.secondary;


import net.focik.Smartgaz.userservice.domain.AppUser;

import java.util.List;

public interface IAppUserRepository {
    AppUser addUser(AppUser user);

    AppUser findUserByUsername(String username);

    AppUser save(AppUser user);

    List<AppUser> findAll();

    AppUser findUserByEmail(String email);

    AppUser findUserById(Long id);

    void deleteUser(Long id);
}
