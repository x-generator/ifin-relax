package com.ifinrelax.repository.expense;

import com.ifinrelax.entity.expense.Expense;
import com.ifinrelax.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Timur Berezhnoi
 */
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    Page<Expense> findPagebelExpensesByUser(User user, Pageable pageable);
    List<Expense> findByUser(User user);
}
