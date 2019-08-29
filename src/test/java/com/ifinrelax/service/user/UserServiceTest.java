package com.ifinrelax.service.user;

import com.ifinrelax.dto.user.UserRegistrationDTO;
import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.entity.role.Role;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.exception.UserAlreadyExistException;
import com.ifinrelax.repository.user.UserRepository;
import com.ifinrelax.service.currency.CurrencyService;
import com.ifinrelax.service.role.RoleService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;

import static com.ifinrelax.constant.ResponseMessage.EMAIL_IN_USE;
import static com.ifinrelax.constant.ResponseMessage.USER_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Timur Berezhnoi
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private CurrencyService currencyService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        when(userRepository.save(any(User.class))).thenReturn(new User("FirstName", "LastName", "email@g.com", "password", new HashSet<Role>() {{add(new Role("USER_ROLE"));}}, new Currency("₴", "UAH")));
        userService = new UserService(userRepository, roleService, currencyService);
    }

    @Test
    public void shouldFailSignUpIfUserAlreadyExist() {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("Otimus", "Prime", "prime@gmail.com", "password", new Currency("₴", "UAH"));
        when(userRepository.findOneByEmail(user.getEmail())).thenReturn(new User("Otimus", "Prime", "prime@gmail.com", "password", new HashSet<Role>() {{add(new Role("USER_ROLE"));}}, new Currency("₴", "UAH")));

        exception.expect(UserAlreadyExistException.class);
        exception.expectMessage(EMAIL_IN_USE.getMessage());

        // When
        userService.createUser(user);

        // Then
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldSignUp() {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("Otimus", "Prime", "newprime@gmail.com", "password", new Currency("₴", "UAH"));
        when(userRepository.findOneByEmail(user.getEmail())).thenReturn(any(User.class));

        // When
        userService.createUser(user);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void shouldFailIfUserNotFound() {
        // Given
        when(userRepository.findOneByEmail(anyString())).thenReturn(null);

        exception.expect(IllegalStateException.class);
        exception.expectMessage(USER_NOT_FOUND.getMessage());

        // When
        userService.getUser("email@g.com");
    }

    @Test
    public void shouldGetUser() {
        // Given
        String email = "prime@gmail.com";

        when(userRepository.findOneByEmail(email)).thenReturn(new User("Otimus", "Prime", email, "password", new HashSet<Role>() {{add(new Role("USER_ROLE"));}}, new Currency("₴", "UAH")));

        // When
        User user = userService.getUser(email);

        // Then
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }
}