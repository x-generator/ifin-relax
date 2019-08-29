package com.ifinrelax.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifinrelax.configuration.MessageSourceConfiguration;
import com.ifinrelax.controller.advice.ControllerAdvice;
import com.ifinrelax.dto.user.UserRegistrationDTO;
import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.entity.expense.Expense;
import com.ifinrelax.entity.role.Role;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.service.user.UserAutoSignUpService;
import com.ifinrelax.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static com.ifinrelax.constant.Route.SIGN_UP;
import static java.time.LocalDate.now;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Timur Berezhnoi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MessageSourceConfiguration.class})
public class UserRegistrationControllerTest {

    @Autowired
    private MessageSource messageSource;

    private UserService userService = mock(UserService.class);
    private UserAutoSignUpService userAutoSignUpService = mock(UserAutoSignUpService.class);
    private UserRegistrationController userRegistrationController = new UserRegistrationController(userService, userAutoSignUpService);

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        User validUser = new User("Prime", "Optimus", "prime@gail.com", "password", new HashSet<Role>(){{add(new Role("ASD"));}}, new Currency("₴", "UAH"));
        validUser.setId(1);
        validUser.setExpenses(singletonList(new Expense("Tsts expense object", validUser, 1000, now())));
        when(userService.createUser(any(UserRegistrationDTO.class))).thenReturn(validUser);

        mockMvc = standaloneSetup(userRegistrationController).setControllerAdvice(new ControllerAdvice(messageSource)).build();
    }

    @Test
    public void shouldReturnFlaseIfUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/isUserAuthenticated"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("isAuthenticated").value(false));
    }

    @Test
    public void shouldRegistrate() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new UserRegistrationDTO("Prime", "Optimus", "prime@gail.com", "password", new Currency("₴", "UAH")));

        mockMvc.perform(post(SIGN_UP).content(requestBody).contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.firstName", is("Prime")))
                .andExpect(jsonPath("$.lastName", is("Optimus")))
                .andExpect(jsonPath("$.email", is("prime@gail.com")))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void shouldFailRegistrationIfCurrencyIsNull() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new UserRegistrationDTO("Prime", "Optimus", "prime@gail.com", "password", null));

        mockMvc.perform(post(SIGN_UP).content(requestBody).contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Currency code can't be null.")));
    }
}