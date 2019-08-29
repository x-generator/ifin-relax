package com.ifinrelax.repository.role;

import com.ifinrelax.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Timur Berezhnoi
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
