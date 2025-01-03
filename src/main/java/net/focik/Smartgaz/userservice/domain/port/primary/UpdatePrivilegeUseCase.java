package net.focik.Smartgaz.userservice.domain.port.primary;


import net.focik.Smartgaz.userservice.domain.Privilege;

public interface UpdatePrivilegeUseCase {
    void updatePrivilegesInUserRole(Long idUser, Privilege privilege);
}
