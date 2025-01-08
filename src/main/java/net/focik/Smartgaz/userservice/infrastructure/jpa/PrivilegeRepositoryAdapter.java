package net.focik.Smartgaz.userservice.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import net.focik.Smartgaz.userservice.domain.Privilege;
import net.focik.Smartgaz.userservice.domain.port.secondary.IPrivilegeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PrivilegeRepositoryAdapter implements IPrivilegeRepository {
    private final IPrivilegeDtoRepository privilegeDtoRepository;


    @Override
    public Privilege getPrivilegeById(Long id) {
        Optional<Privilege> byId = privilegeDtoRepository.findById(id);

        return byId.orElse(null);
    }

    @Override
    public Privilege save(Privilege privilege) {
        return privilegeDtoRepository.save(privilege);
    }
}
