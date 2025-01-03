package net.focik.Smartgaz.userservice.domain.port.primary;

public interface DeletePrivilegeUseCase {
    void deleteUsersRoleById(Long id, Long idRole);
}
