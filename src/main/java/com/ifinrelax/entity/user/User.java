package com.ifinrelax.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.entity.expense.Expense;
import com.ifinrelax.entity.role.Role;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.LazyCollectionOption.FALSE;

/**
 * @author Timur Berezhnoi
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;

    @OneToMany(fetch = EAGER, cascade = REMOVE)
    private Set<Role> roles;

    @LazyCollection(FALSE)
    @OneToMany(mappedBy = "user", cascade = REMOVE, fetch = LAZY)
    private List<Expense> expenses;

    @OneToOne
    private Currency currency;

    public User(String firstName, String lastName, String email, String password, Set<Role> roles, Currency currency) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.currency = currency;
    }

    public User() {}

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", currency='" + currency + '\'' +
                '}';
    }
}