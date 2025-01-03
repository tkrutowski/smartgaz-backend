package net.focik.Smartgaz.userservice.domain.port.secondary;


import net.focik.Smartgaz.userservice.domain.Role;

import java.util.List;

public interface RoleRepository {

    Role addRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(Long id);
}
