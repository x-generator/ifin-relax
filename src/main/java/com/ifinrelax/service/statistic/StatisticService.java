package com.ifinrelax.service.statistic;

import com.ifinrelax.dto.expense.ExpenseStatisticDTO;
import com.ifinrelax.entity.expense.Expense;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.repository.expense.ExpenseRepository;
import com.ifinrelax.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Timur Berezhnoi
 */
@Service
@Transactional
public class StatisticService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    @Autowired
    public StatisticService(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    public Set<Integer> getExpensessYears(String email) {
        User currentUser = userService.getUser(email);
        Set<Integer> years = currentUser.getExpenses().stream().map(expense -> expense.getCreatedAt().getYear()).collect(Collectors.toSet());
        Set<Integer> sorted = new TreeSet<>(Comparator.reverseOrder());
        sorted.addAll(years);
        return sorted;
    }

    /**
     * Fetch user's expense statistic.
     *
     * @param userEmail is an user's email.
     * @param year - the year of statistic.
     */
    public Set<ExpenseStatisticDTO> getUserExpenseStatisticForYear(String userEmail, int year) {
        List<Expense> userExpenses = expenseRepository.findByUser(userService.getUser(userEmail));

        Set<ExpenseStatisticDTO> result = new HashSet<>();

        Map<Month, Long> monthToToalSpent = new HashMap<>();

        for (Expense userExpens : userExpenses) {
            if(userExpens.getCreatedAt().getYear() == year) {
                Month currentMonth = userExpens.getCreatedAt().getMonth();
                monthToToalSpent.put(currentMonth, monthToToalSpent.getOrDefault(currentMonth, 0L) + userExpens.getAmount());
            }
        }

        for (Month month : monthToToalSpent.keySet()) {
            result.add(new ExpenseStatisticDTO(month, monthToToalSpent.get(month)));
        }

        return result;
    }
}