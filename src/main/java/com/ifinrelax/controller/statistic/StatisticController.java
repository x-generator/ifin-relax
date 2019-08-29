package com.ifinrelax.controller.statistic;

import com.ifinrelax.dto.expense.ExpenseStatisticDTO;
import com.ifinrelax.service.statistic.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;

import static com.ifinrelax.constant.Route.EXPENSE_STATISTIC;
import static com.ifinrelax.constant.Route.EXPENSE_STATISTIC_YEARS;

/**
 * @author Timur Berezhnoi
 */
@RestController
public class StatisticController {

    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping(EXPENSE_STATISTIC_YEARS)
    public Set<Integer> getStatisticAvailableYears(Principal principal) {
        return statisticService.getExpensessYears(principal.getName());
    }

    @GetMapping(EXPENSE_STATISTIC)
    public Set<ExpenseStatisticDTO> getExpenseStatistic(@PathVariable int year, Principal principal) {
        return statisticService.getUserExpenseStatisticForYear(principal.getName(), year);
    }
}
