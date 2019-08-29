package com.ifinrelax.service.expense;

import com.ifinrelax.dto.expense.ExpenseDTO;
import com.ifinrelax.dto.expense.PageableExpensesDTO;
import com.ifinrelax.entity.expense.Expense;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.exception.EntityNotFoundException;
import com.ifinrelax.repository.expense.ExpenseRepository;
import com.ifinrelax.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.ifinrelax.constant.ResponseMessage.EXPENSE_NOT_FOUND;
import static com.ifinrelax.constant.ResponseMessage.FORBIDEN_UPDATE_FOREIGN_EXPENSE;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * @author Timur Berezhnoi
 */
@Service
@Transactional(isolation = Isolation.DEFAULT)
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final DateTimeFormatter dateTimeFormatter = ofPattern("M/d/y");

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    /**
     * Fetchs user's expenses.
     *
     * @param userEmail is an user's email.
     * @param page      page
     */
    public PageableExpensesDTO getUserExpenses(String userEmail, Pageable page) {
        User currentUser = userService.getUser(userEmail);
        PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), Sort.Direction.DESC, "createdAt");
        Page<Expense> expenses = expenseRepository.findPagebelExpensesByUser(currentUser, pageRequest);
        List<Expense> userExpenses = expenseRepository.findByUser(currentUser);

        long totalAmount = userExpenses.stream().mapToLong(Expense::getAmount).sum();
        return new PageableExpensesDTO(expenses, totalAmount);
    }

    /**
     * Creates a new expense for a user.
     *
     * @param expenseDTO is an dto from client.
     */
    public void addExpense(ExpenseDTO expenseDTO, String userEmail) {
        expenseRepository.save(new Expense(expenseDTO.getObject(), userService.getUser(userEmail), expenseDTO.getAmount(),
                                parse(expenseDTO.getCreatedAt(), dateTimeFormatter)));
    }

    /**
     * Updates user's expense.
     *
     * @param expenseDTO    - an expense data to update.
     * @param principalName - current logged in user.
     * @throws EntityNotFoundException if expense is null.
     * @throws IllegalStateException if an expense doesn't belong to a user.
     */
    public void updateExpense(ExpenseDTO expenseDTO, String principalName) {
        Expense currentExpense = expenseRepository.findOne(expenseDTO.getId());

        if(currentExpense == null) {
            throw new EntityNotFoundException(EXPENSE_NOT_FOUND.getMessage());
        }

        String expenseUserEmail = currentExpense.getUser().getEmail();
        if(!expenseUserEmail.equals(principalName)) {
            throw new IllegalStateException(FORBIDEN_UPDATE_FOREIGN_EXPENSE.getMessage());
        }

        currentExpense.setAmount(expenseDTO.getAmount());
        currentExpense.setObject(expenseDTO.getObject());
        currentExpense.setCreatedAt(parse(expenseDTO.getCreatedAt(), dateTimeFormatter));
        expenseRepository.save(currentExpense);
    }

    /**
     * This method deletes user's expense by given user email and expense id.
     * If expense doesn't belong to a user the expense will not be deleted.
     *
     * @param userEmail - user's unique identefire.
     * @param expenseId - expense's id to delete.
     */
    public void deleteUserExpense(String userEmail, int expenseId) {
        Expense expense = expenseRepository.findOne(expenseId);
        if(expense != null && expense.getUser().getEmail().equals(userEmail)) {
            expenseRepository.delete(expense);
        }
    }
}