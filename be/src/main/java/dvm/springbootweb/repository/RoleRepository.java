package dvm.springbootweb.repository;

import dvm.springbootweb.entity.ERole;
import dvm.springbootweb.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(ERole roleName);
}
