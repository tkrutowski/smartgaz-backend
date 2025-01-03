package net.focik.Smartgaz.userservice.infrastructure.jpa;

import net.focik.Smartgaz.userservice.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

interface IPrivilegeDtoRepository extends JpaRepository<Privilege, Long> {
//    Optional<Privilege> findByName(String name);
}
