package com.ifinrelax.constant;

/**
 * Response messages that can be used in response object.
 *
 * @author Timur Berezhnoi
 */
public enum ResponseMessage {

    EMAIL_IN_USE("Email is already in use!"),
    FORBIDEN_UPDATE_FOREIGN_EXPENSE("Can't update foreign expense."),
    USER_NOT_FOUND("User not found."),
    EXPENSE_NOT_FOUND("Expense not found.");

    private final String message;

    ResponseMessage(final String message) {
        this.message = message;
    }

    /**
     * The method returns response message.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }
}