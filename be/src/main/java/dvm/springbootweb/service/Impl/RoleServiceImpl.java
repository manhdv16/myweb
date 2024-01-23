package dvm.springbootweb.service.Impl;

import dvm.springbootweb.entity.ERole;
import dvm.springbootweb.entity.Role;
import dvm.springbootweb.repository.RoleRepository;
import dvm.springbootweb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByRoleName(ERole roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
