package com.ifinrelax.dto.expense;

import java.time.Month;
import java.util.Objects;

import static java.util.Objects.hash;

/**
 * @author Timur Berezhnoi
 */
public class ExpenseStatisticDTO {

    private final Month month;
    private final long totalSpent;

    public ExpenseStatisticDTO(Month month, long totalSpent) {
        this.month = month;
        this.totalSpent = totalSpent;
    }

    public Month getMonth() {
        return month;
    }

    public long getTotalSpent() {
        return totalSpent;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        ExpenseStatisticDTO that = (ExpenseStatisticDTO) o;
        return month == that.month;
    }

    @Override
    public int hashCode() {
        return hash(month);
    }

    @Override
    public String toString() {
        return "ExpenseStatisticDTO{" +
                "month='" + month + '\'' +
                ", totalSpent=" + totalSpent +
                '}';
    }
}
