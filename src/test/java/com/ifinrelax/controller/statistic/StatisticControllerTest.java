package com.ifinrelax.controller.statistic;

import com.ifinrelax.configuration.MessageSourceConfiguration;
import com.ifinrelax.controller.advice.ControllerAdvice;
import com.ifinrelax.dto.expense.ExpenseStatisticDTO;
import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.entity.role.Role;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.service.statistic.StatisticService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.ifinrelax.constant.Route.EXPENSE_STATISTIC;
import static com.ifinrelax.constant.Route.EXPENSE_STATISTIC_YEARS;
import static java.time.Month.DECEMBER;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Timur Berezhnoi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MessageSourceConfiguration.class})
public class StatisticControllerTest {

    @Autowired
    private MessageSource messageSource;

    private StatisticService statisticService = mock(StatisticService.class);

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        User user = new User("Optimus", "Prime", "email@g.com", "password", new HashSet<Role>() {{
            add(new Role("TEST_ROLE"));
        }}, new Currency("$", "USD"));
        user.setId(1);

        Set<Integer> sorted = new TreeSet<>(Comparator.reverseOrder());
        sorted.addAll(new HashSet<>(asList(2017, 2016, 2015)));
        when(statisticService.getExpensessYears(anyString())).thenReturn(sorted);

        mockMvc = standaloneSetup(new StatisticController(statisticService))
                .setControllerAdvice(new ControllerAdvice(messageSource))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void shouldReturnAllAvailableYearsForStatistic() throws Exception {
        mockMvc.perform(get(EXPENSE_STATISTIC_YEARS).principal(() -> "email@g.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]", is(2017)))
                .andExpect(jsonPath("$[1]", is(2016)))
                .andExpect(jsonPath("$[2]", is(2015)));
    }

    @Test
    public void shouldReturnTotalExpenseByAllPeriodInAYear() throws Exception {
        when(statisticService.getUserExpenseStatisticForYear(anyString(), anyInt())).thenReturn(new HashSet<ExpenseStatisticDTO>() {{
            add(new ExpenseStatisticDTO(DECEMBER, 1));
            add(new ExpenseStatisticDTO(DECEMBER, 1000));
        }});

        mockMvc.perform(get(EXPENSE_STATISTIC, 2017).principal(() -> "email@g.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].month", is("DECEMBER")))
                .andExpect(jsonPath("$[0].totalSpent", is(1)));
    }
}
