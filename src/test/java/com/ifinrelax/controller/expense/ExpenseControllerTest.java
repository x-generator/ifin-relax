package com.ifinrelax.controller.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifinrelax.configuration.MessageSourceConfiguration;
import com.ifinrelax.controller.advice.ControllerAdvice;
import com.ifinrelax.dto.expense.ExpenseDTO;
import com.ifinrelax.dto.expense.PageableExpensesDTO;
import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.entity.expense.Expense;
import com.ifinrelax.entity.role.Role;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.service.expense.ExpenseService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static com.ifinrelax.constant.Route.EXPENSE;
import static com.ifinrelax.constant.Route.USER_EXPENSES;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static java.time.Month.APRIL;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Timur Berezhnoi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MessageSourceConfiguration.class})
public class ExpenseControllerTest {

    @Autowired
    private MessageSource messageSource;

    private ExpenseService expenseService = mock(ExpenseService.class);

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        User user = new User("Optimus", "Prime", "email@g.com", "password", new HashSet<Role>() {{
            add(new Role("TEST_ROLE"));
        }}, new Currency("$", "USD"));
        user.setId(1);
        when(expenseService.getUserExpenses(anyString(), any(Pageable.class)))
                .thenReturn(new PageableExpensesDTO(
                        new PageImpl<>(
                                asList(new Expense("Testexpense object",
                                       user,
                                       1000,
                                       now()),
                                       new Expense(
                                       "Testexpense object 2",
                                       user,
                                       1000,
                                       now()))
                        ), 2000));

        mockMvc = standaloneSetup(new ExpenseController(expenseService))
                                    .setControllerAdvice(new ControllerAdvice(messageSource))
                                    .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                    .build();
    }

    @Test
    public void shouldReturnListOfExpenses() throws Exception {
        mockMvc.perform(get(USER_EXPENSES).principal(() -> "email@g.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.expensesPage.content", hasSize(2)))
                .andExpect(jsonPath("$.expensesPage.totalPages", is(1)))
                .andExpect(jsonPath("$.expensesPage.totalElements", is(2)))
                .andExpect(jsonPath("$.totalAmount", is(2000)));
    }

    @Test
    public void shouldCreateExpenses() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, "Expense object.", 100, now().format(ofPattern("M/d/y"))));

        mockMvc.perform(post(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void shouldReturnBadRequestIfExpenseObjectIsNull() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, null, 100, now().format(ofPattern("M/d/y"))));

        mockMvc.perform(post(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void shouldReturnBadRequestIfExpenseObjectIsEmpty() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, "", 100, now().format(ofPattern("M/d/y"))));

        mockMvc.perform(post(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Expense object can not be null or empty.")));
    }

    @Test
    public void shouldReturnBadRequestIfExpenseAmountIsNegative() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, "SDSAD", -1, now().format(ofPattern("M/d/y"))));

        mockMvc.perform(post(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Expense amount can not be negative value or 0.")));
    }

    @Test
    public void shouldReturnBadRequestIfExpenseAmountIsZero() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, "SDSAD", 0, now().format(ofPattern("M/d/y"))));

        mockMvc.perform(post(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Expense amount can not be negative value or 0.")));
    }

    @Test
    public void shouldReturnBadRequestIfExpenseCreatedAtIsNull() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, "SDSAD", 12, null));

        mockMvc.perform(post(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Expense date of creation can not be null.")));
    }

    @Test
    @Ignore("Ignored because back end part validation is not implemented yet")
    public void shouldReturnBadRequestIfExpenseCreatedAtIsInFuture() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, "SDSAD", 123, of(3000, APRIL, 1).format(ofPattern("M/d/y"))));

        mockMvc.perform(post(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Date can not be after today.")));
    }

    @Test
    public void shouldReturnBadRequestIfExpenseObjectLengthIsMoreThan50Chars() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, "askjdhasdhaksdjhaksdjhksajdhkasjdhaksjhdkasjhdsakjdhsjsjj",
                2, now().format(ofPattern("M/d/y"))));

        mockMvc.perform(post(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Expense object can not be more than 50 characters.")));
    }

    @Test
    @Ignore("Ignored because back end part validation is not implemented yet")
    public void shouldReturnBadRequestIfExpenseObjectIsInvalid() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, "askjdhasdhaksdjhaksdjhksajdhkasjdhaksjhdkasjhdsakjdhsjsjj",
                0, "1/1/3000"));

        mockMvc.perform(post(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void shouldUpdateAnExpense() throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(new ExpenseDTO(1, "Expense object", 10, "1/1/2010"));

        mockMvc.perform(patch(EXPENSE).content(requestBody).contentType(APPLICATION_JSON).principal(() -> "email@g.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void shouldDeletAnExpense() throws Exception {
        mockMvc.perform(delete(EXPENSE + "/1").principal(() -> "email@g.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}