package com.ifinrelax.dto.user;

import com.ifinrelax.configuration.MessageSourceConfiguration;
import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.exception.UserAlreadyExistException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static com.ifinrelax.util.ConstraintViolationHolder.getMessages;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * @author Timur Berezhnoi
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MessageSourceConfiguration.class})
public class UserRegistartionDTOTest {

    @Autowired
    private MessageSource messageSource;

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldFailIfEmailIsInvalid() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("First", "Last", "invalid email format", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Email is in wrong format.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfEmailIsMoreThan25Symbols() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastName", "aaaaaaaaaaaaaaaaaaaaaaaa@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Email min/max length is 7/25.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfEmailIsLessThan7Symbols() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastName", "ab@g.c", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Email min/max length is 7/25.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfEmailIsNull() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastName", null, "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Email can't be null.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfFirstNameIsMoreThan15Chars() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstnameFirstnameFirstnameFirstnameFirstname", "LastName", "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("First name min/max length is 3/15.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfFirstNameIsLessThan3Chars() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("Fi", "LastName", "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("First name min/max length is 3/15.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfFirstNameContainsSpace() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("First name", "LastName", "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("First name can't contain special characters.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfFirstNameConatainsSpecialCharacters() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("!@#$%^&*()+=?/.><,{}[]\"\t\r\n':;|\\", "LastName", "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);
        int expextedMessageCount = 2;

        List<String> messages = getMessages(messageSource, constraintViolations);

        // Then
        assertEquals(expextedMessageCount, constraintViolations.size());
        assertTrue(messages.contains("First name min/max length is 3/15."));
        assertTrue(messages.contains("First name can't contain special characters."));
    }

    @Test
    public void shouldFailIfUserFirstNameIsNull() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO(null, "LastName", "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("First name can not be null.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailfIfLastNameIsNull() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", null, "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Last name can not be null.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailfIfLastNameIsMoreThan15Characters() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastNameLastNameLastNameLastNameLastNameLastNameLastName", "tima@g.com", "password", new Currency());

        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Last name min/max length is 3/15.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIsLastNameIsLessThan3Characters() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "La", "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Last name min/max length is 3/15.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIsLastNameContainsSpace() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "La ASD", "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Last name can't contain special characters.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIsLastNameContainsSpecialCharacters() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "!@#$%^&*()+=?/.><,{}[]\"\t\n':;|\\", "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);
        int expextedMessageCount = 2;

        List<String> messages = getMessages(messageSource, constraintViolations);

        // Then
        assertEquals(expextedMessageCount, constraintViolations.size());
        assertTrue(messages.contains("Last name can't contain special characters."));
        assertTrue(messages.contains("Last name min/max length is 3/15."));
    }

    @Test
    public void shouldFailIfPasswordIsNull() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastName", "tima@g.com", null, new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Password can not be null.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfPasswordIsMoreThan15Characters() {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastName", "tima@g.com", "qwertyuiopasdfgh", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Password min/max length is 3/15.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfPasswordIsLessThan6Characters() throws UserAlreadyExistException {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastName", "tima@g.com", "qwer", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Password min/max length is 3/15.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailfIfCurrencyCodeIsNull() {
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastName", "tima@g.com", "qwerty", null);
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Currency code can't be null.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailfIfCurrencyCodeIsMoreThan3Characters() {
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastName", "tima@g.com", "qwerty", new Currency("$", "US"));
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Currency code min/max length is 3 characters.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailfIfCurrencyCodeIsLessThan3Characters() {
        UserRegistrationDTO user = new UserRegistrationDTO("FirstName", "LastName", "tima@g.com", "qwerty", new Currency("$", "USDSxc"));
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Currency code min/max length is 3 characters.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void someCombainedValidation() {
        // Given - setUp();
        UserRegistrationDTO user = new UserRegistrationDTO("First Name sdasdasdasdasdsad", "Last asdasdasdasasdasdasdasdasdName",
                                                            "txzzxxxxxxxxxxxxzzzzima@g.com", null, new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(6, constraintViolations.size());
    }

    @Test
    public void userRegistrationDTOValid() {
        UserRegistrationDTO user = new UserRegistrationDTO("Optimus", "Prime", "tima@g.com", "password", new Currency());
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations = validator.validate(user);

        // Then
        assertEquals(0, constraintViolations.size());
    }
}