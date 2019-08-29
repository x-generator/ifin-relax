package com.ifinrelax.service.expense;

import com.ifinrelax.dto.expense.ExpenseDTO;
import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.entity.expense.Expense;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.exception.EntityNotFoundException;
import com.ifinrelax.repository.expense.ExpenseRepository;
import com.ifinrelax.service.user.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.HashSet;

import static com.ifinrelax.constant.ResponseMessage.EXPENSE_NOT_FOUND;
import static com.ifinrelax.constant.ResponseMessage.FORBIDEN_UPDATE_FOREIGN_EXPENSE;
import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Timur Berezhnoi
 */
@RunWith(MockitoJUnitRunner.class)
public class ExpenseServiceTest {

    private ExpenseService expenseService;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserService userService;

    @Rule
    public ExpectedException expectedException = none();

    @Before
    public void setUp() {
        expenseService = new ExpenseService(expenseRepository, userService);
    }

    @Test
    public void shouldThrowAnExceptionIfUserIsNotEist() {
        when(userService.getUser(anyString())).thenThrow(new IllegalStateException("User not found."));
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("User not found.");

        Pageable pageable = mock(Pageable.class);
        expenseService.getUserExpenses("trash@g.com", pageable);

        verify(expenseRepository, never()).findPagebelExpensesByUser(any(User.class), pageable);
    }

    @Test
    public void shouldAddAnExpense() {
        User user = new User("First name", "Last name", "email@g.com", "password", new HashSet<>(), new Currency("₴", "UAH"));
        when(userService.getUser(anyString())).thenReturn(user);
        when(expenseRepository.save(any(Expense.class))).thenReturn(any(Expense.class));

        // When
        expenseService.addExpense(new ExpenseDTO(1, "Expense Object.", 1000, now().format(ofPattern("M/d/y"))), "email@g.com");

        // Then
        verify(expenseRepository, only()).save(any(Expense.class));
    }

    @Test
    public void shouldUpdateAnExpense() {
        // Given
        User user = new User("First name", "Last name", "email@g.com", "password", new HashSet<>(), new Currency("₴", "UAH"));
        LocalDate now = now();
        ExpenseDTO expenseDTOToSave = new ExpenseDTO(1, "Expense Object.", 1000, now.format(ofPattern("M/d/y")));
        Expense expenseToSave = new Expense("Expense Object.", user, 1000, now);
        expenseToSave.setId(1);

        when(expenseRepository.findOne(1)).thenReturn(expenseToSave);

        // When
        expenseService.updateExpense(expenseDTOToSave, "email@g.com");

        // Then
        verify(expenseRepository, times(1)).save(expenseToSave);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenUpdateAnForeignExpense() {
        // Given
        User user = new User("First name", "Last name", "email2@g.com", "password", new HashSet<>(), new Currency("₴", "UAH"));
        LocalDate now = now();
        ExpenseDTO expenseDTOToSave = new ExpenseDTO(1, "Expense Object.", 1000, now.format(ofPattern("M/d/y")));
        Expense expenseToSave = new Expense("Expense Object.", user, 1000, now);
        expenseToSave.setId(1);

        when(expenseRepository.findOne(1)).thenReturn(expenseToSave);

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(FORBIDEN_UPDATE_FOREIGN_EXPENSE.getMessage());

        // When
        expenseService.updateExpense(expenseDTOToSave, "email@g.com");

        // Then
        verify(expenseRepository, never()).save(expenseToSave);
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenExpenseDoesntExist() {
        // Given
        User user = new User("First name", "Last name", "email2@g.com", "password", new HashSet<>(), new Currency("₴", "UAH"));
        LocalDate now = now();
        ExpenseDTO expenseDTOToSave = new ExpenseDTO(1, "Expense Object.", 1000, now.format(ofPattern("M/d/y")));
        Expense expenseToSave = new Expense("Expense Object.", user, 1000, now);
        expenseToSave.setId(1);

        when(expenseRepository.findOne(1)).thenReturn(null);

        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage(EXPENSE_NOT_FOUND.getMessage());

        // When
        expenseService.updateExpense(expenseDTOToSave, "email@g.com");

        // Then
        verify(expenseRepository, never()).save(expenseToSave);
    }
}
