package com.ifinrelax.controller.expense;

import com.ifinrelax.dto.expense.ExpenseDTO;
import com.ifinrelax.dto.expense.PageableExpensesDTO;
import com.ifinrelax.service.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.ifinrelax.constant.Route.EXPENSE;
import static com.ifinrelax.constant.Route.ID_PATH_VARIABLE;
import static com.ifinrelax.constant.Route.USER_EXPENSES;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Timur Berezhnoi
 */
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Hnalder for fetching all expensess for a logged in user in a session.
     *
     * @param principal is logged in user in a session.
     * @return PageableExpensesDTO
     */
    @GetMapping(USER_EXPENSES)
    public PageableExpensesDTO getListExpensesForUser(Principal principal, Pageable pageable) {
        return expenseService.getUserExpenses(principal.getName(), pageable);
    }

    @PostMapping(EXPENSE)
    public ResponseEntity<?> addExpense(@Valid @RequestBody ExpenseDTO expenseDTO, Principal principal) {
        expenseService.addExpense(expenseDTO, principal.getName());
        return ok().build();
    }

    @PatchMapping(EXPENSE)
    public ResponseEntity<?> updateExpense(@Valid @RequestBody ExpenseDTO expenseDTO, Principal principal) {
        expenseService.updateExpense(expenseDTO, principal.getName());
        return ok().build();
    }

    @DeleteMapping(EXPENSE + ID_PATH_VARIABLE)
    public ResponseEntity<?> deleteExpense(@PathVariable int id, Principal principal) {
        expenseService.deleteUserExpense(principal.getName(), id);
        return ok().build();
    }
}