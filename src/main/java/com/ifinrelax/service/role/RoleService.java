package com.ifinrelax.service.role;

import com.ifinrelax.entity.role.Role;
import com.ifinrelax.repository.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Timur Berezhnoi
 */
@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
