package com.ifinrelax.entity.expense;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ifinrelax.entity.user.User;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Entity for expense representation.
 *
 * @author Timur Berezhnoi
 */
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;

    private String object;

    /**
     * An amount is represented in a hundredth part.
     */
    private int amount;

    @JsonFormat(pattern = "M/d/y")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDate createdAt;

    public Expense(String object, User user, int amount, LocalDate createdAt) {
        this.object = object;
        this.user = user;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Expense() {}

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
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

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "object='" + object + '\'' +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }
}
