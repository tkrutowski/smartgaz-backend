package net.focik.Smartgaz.userservice.infrastructure.jpa;

import net.focik.Smartgaz.userservice.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface IUserDtoRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

}
