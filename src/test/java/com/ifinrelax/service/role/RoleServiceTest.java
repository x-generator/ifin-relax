package com.ifinrelax.service.role;

import com.ifinrelax.entity.role.Role;
import com.ifinrelax.repository.role.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.notNull;

/**
 * @author Timur Berezhnoi
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void shouldReturnRole() {
        String roleName = "TEST_ROLE2";
        when(roleService.findByRoleName(roleName)).thenReturn(new Role(roleName));
        notNull(roleService.findByRoleName(roleName), "Something went wrong...");
    }
}