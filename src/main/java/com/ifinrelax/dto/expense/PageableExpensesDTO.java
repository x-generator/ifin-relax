package com.ifinrelax.dto.expense;

import com.ifinrelax.entity.expense.Expense;
import org.springframework.data.domain.Page;

/**
 * @author Timur Berezhnoi
 */
public class PageableExpensesDTO {
    private Page<Expense> expensesPage;
    private long totalAmount;

    public PageableExpensesDTO(Page<Expense> expensesPage, long totalAmount) {
        this.expensesPage = expensesPage;
        this.totalAmount = totalAmount;
    }

    public void setExpensesPage(Page<Expense> expensesPage) {
        this.expensesPage = expensesPage;
    }

    public Page<Expense> getExpensesPage() {
        return expensesPage;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "PageableExpensesDTO{" +
                "expensesPage=" + expensesPage +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
