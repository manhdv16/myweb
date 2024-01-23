package dvm.springbootweb.service;

import dvm.springbootweb.entity.ERole;
import dvm.springbootweb.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleName(ERole roleName);
}
