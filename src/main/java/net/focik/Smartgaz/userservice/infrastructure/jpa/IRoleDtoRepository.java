package net.focik.Smartgaz.userservice.infrastructure.jpa;

import net.focik.Smartgaz.userservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDtoRepository extends JpaRepository<Role, Long> {
}
