package net.focik.Smartgaz.userservice.domain.port.primary;




import net.focik.Smartgaz.userservice.domain.Privilege;
import net.focik.Smartgaz.userservice.domain.Role;

import java.util.List;

public interface GetPrivilegesUseCase {
    List<Privilege> getUserPrivileges(Long idUser);

    List<Role> getRoles();


    List<Privilege> getPrivilegesByUser(Long idUser);

    Privilege getRoleDetails(Long idUser, Long idRole);
}
