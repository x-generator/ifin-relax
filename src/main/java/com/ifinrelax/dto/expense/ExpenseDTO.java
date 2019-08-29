package com.ifinrelax.dto.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Timur Berezhnoi
 */
public class ExpenseDTO {

    private int id;

    @NotEmpty(message = "expense.object.null")
    @Size(max = 50, message = "expense.object.length")
    private String object;

    @Min(value = 1, message = "expense.amount.negative")
    private int amount;

    @NotNull(message = "expense.date.null")
    private String createdAt;

    public ExpenseDTO(@JsonProperty("id") int id,
                      @JsonProperty("object") String object,
                      @JsonProperty("amount") int amount,
                      @JsonProperty("createdAt") String createdAt) {
        this.id = id;
        this.object = object;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getObject() {
        return object;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "ExpenseDTO{" +
                "id=" + id +
                ", object='" + object + '\'' +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }
}
