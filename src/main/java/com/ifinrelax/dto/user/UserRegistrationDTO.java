package com.ifinrelax.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifinrelax.entity.currency.Currency;
import org.hibernate.validator.constraints.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Timur Berezhnoi
 */
public class UserRegistrationDTO {

    @NotNull(message = "user.firstName.null")
    @Size(min = 3, max = 15, message = "user.firstName.length")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "user.firstName.invalid")
    private String firstName;

    @NotNull(message = "user.lastName.null")
    @Size(min = 3, max = 15, message = "user.lastName.length")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "user.lastName.invalid")
    private String lastName;

    @NotNull(message = "user.email.null")
    @Email(regexp = "^[a-z0-9-\\+]+(\\.[a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9]+)$", message = "user.email.invalid")
    @Size(min = 7, max = 25, message = "user.email.length")
    private String email;

    @NotNull(message = "user.password.null")
    @Size(min = 6, max = 15, message = "user.password.length")
    private String password;

    @NotNull(message = "user.currency.null")
    @Valid
    private Currency currency;

    @JsonCreator
    public UserRegistrationDTO(@JsonProperty("firstName") String firstName,
                               @JsonProperty("lastName") String lastName,
                               @JsonProperty("email") String email,
                               @JsonProperty("password") String password,
                               @JsonProperty("currency") Currency currency)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.currency = currency;
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

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", currencyCode='" + currency + '\'' +
                '}';
    }
}
