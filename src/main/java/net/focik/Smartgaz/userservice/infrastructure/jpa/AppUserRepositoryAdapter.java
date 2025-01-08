package net.focik.Smartgaz.userservice.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import net.focik.Smartgaz.userservice.domain.AppUser;
import net.focik.Smartgaz.userservice.domain.port.secondary.IAppUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AppUserRepositoryAdapter implements IAppUserRepository {

    private final IUserDtoRepository userDtoRepository;

    @Override
    public AppUser addUser(AppUser user) {
        return null;
    }

    @Override
    public AppUser findUserByUsername(String username) {
        Optional<AppUser> byUserName = userDtoRepository.findByUsername(username);

        return byUserName.orElse(null);
    }

    @Override
    public AppUser save(AppUser user) {
        return userDtoRepository.save(user);
    }

    @Override
    public List<AppUser> findAll() {
        return userDtoRepository.findAll();
    }

    @Override
    public AppUser findUserByEmail(String email) {
        Optional<AppUser> byEmail = userDtoRepository.findByEmail(email);

        if (byEmail.isEmpty())
            return null;

        return byEmail.get();
    }

    @Override
    public AppUser findUserById(Long id) {
        int i = 0;
        Optional<AppUser> byId = userDtoRepository.findById(id);
        if (byId.isEmpty())
            return null;

        return byId.get();
    }

    @Override
    public void deleteUser(Long id) {
        userDtoRepository.deleteById(id);
    }
}
