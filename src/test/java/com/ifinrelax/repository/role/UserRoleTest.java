package com.ifinrelax.repository.role;

import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.entity.role.Role;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.repository.currency.CurrencyRepository;
import com.ifinrelax.repository.role.RoleRepository;
import com.ifinrelax.repository.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static org.junit.Assert.assertNull;

/**
 * @author Timur Berezhnoi
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest(showSql = false)
public class UserRoleTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private User user;

    @Before
    public void setUp() {
        Role role = roleRepository.save(new Role("TEST_ROLE"));
        Currency currency = currencyRepository.save(new Currency("$", "USD"));
        user = new User("Prime", "Optimus", "email@g.com", "password", new HashSet<>() {{
            add(role);
        }}, currency);

        userRepository.save(user);
    }

    @Test
    public void shouldDeleteUserRoleIfUserDelete() {
        userRepository.delete(user);
        assertNull(roleRepository.findOne(user.getRoles().iterator().next().getId()));
    }
}
