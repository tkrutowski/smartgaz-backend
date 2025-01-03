package net.focik.Smartgaz.userservice.domain.port.secondary;


import net.focik.Smartgaz.userservice.domain.Privilege;

public interface IPrivilegeRepository {

    //    Role addRole(Role role);
//    List<Role> getAllRoles();
//     Privilege getPrivilegeByName(String name);
    Privilege getPrivilegeById(Long id);

    Privilege save(Privilege privilege);
}
