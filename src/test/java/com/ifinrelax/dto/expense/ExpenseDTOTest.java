package com.ifinrelax.dto.expense;

import com.ifinrelax.configuration.MessageSourceConfiguration;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * @author Timur Berezhnoi
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MessageSourceConfiguration.class})
public class ExpenseDTOTest {

    @Autowired
    private MessageSource messageSource;

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @Ignore("Future dtae validation is not implemented yet")
    public void shouldFailIfDateIsAаterToday() {
        // Given - setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, "Expense object.", 1000, "05/20/2100");
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Date can not be after today.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfAmountIsNegative() {
        // Given - setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, "Expense object.", -1, "05/20/2100");
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Expense amount can not be negative value or 0.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    @Ignore("Future dtae validation is not implemented yet")
    public void shouldFailIfDateIsFutureAndAmountIsNegative() {
        // Diven -setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, "Expense object.", -1, "05/20/2100");
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        List<String> messages = getMessages(messageSource, constraintViolations);

        // Then
        assertEquals(2, constraintViolations.size());
        assertTrue(messages.contains("Date can not be after today."));
        assertTrue(messages.contains("Expense amount can not be negative value or 0."));
    }

    @Test
    public void shouldSuccessIfDateIsToday() {
        // Given - setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, "Expense object.", 12, now().format(ofPattern("M/d/y")));
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        // Then
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void shouldFailIfAmountIsZero() {
        // Given - setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, "Expense object.", 0, now().format(ofPattern("M/d/y")));
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Expense amount can not be negative value or 0.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shoudSuccessIfAmountIsMoreThanZero() {
        // Given - setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, "Expense object.", 1, now().format(ofPattern("M/d/y")));
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        // Then
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void shouldFailIfExpenseObjectIsNull() {
        // Given - setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, null, 1, now().format(ofPattern("M/d/y")));
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Expense object can not be null or empty.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfCreatedAtIsNull() {
        // Given - setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, "Object", 1, null);
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Expense date of creation can not be null.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfExpenseObjectIsEmptyString() {
        // Given - setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, "", 1, now().format(ofPattern("M/d/y")));
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Expense object can not be null or empty.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }

    @Test
    public void shouldFailIfExpenseObjectLengthIsMoreThant50Chars() {
        // Given - setUp();
        ExpenseDTO expenseDTO = new ExpenseDTO(1, "sadasshasdgjashdgjashdgjashdgjashdgshsadsadakshdkas", 1, now().format(ofPattern("M/d/y")));
        Set<ConstraintViolation<ExpenseDTO>> constraintViolations = validator.validate(expenseDTO);

        // Then
        assertEquals(1, constraintViolations.size());
        assertEquals("Expense object can not be more than 50 characters.", messageSource.getMessage(constraintViolations.iterator().next().getMessage(), null, getLocale()));
    }
}