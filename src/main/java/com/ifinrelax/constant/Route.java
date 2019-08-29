package com.ifinrelax.constant;

import javax.naming.OperationNotSupportedException;

/**
 * Route class is just placeholder for constants.
 *
 * @author Timur Berezhnoi
 */
public final class Route {

    public static final String USER_EXPENSES = "/expenses";
    public static final String EXPENSE = "/expense";
    public static final String EXPENSE_STATISTIC = "/expenseStatistic/{year}";
    public static final String EXPENSE_STATISTIC_YEARS = "/expenseStatisticYears";

    public static final String SIGN_IN = "/signIn";
    public static final String SIGN_UP = "/signUp";
    public static final String SIGN_OUT = "/signOut";
    public static final String CURRENCIES = "/currencies";

    public static final String ID_PATH_VARIABLE = "/{id}";

    /**
     * The instance should not be created.
     *
     * @throws OperationNotSupportedException during creation an instance in the class.
     */
    private Route() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Do not instanciate the Route class, this is util class");
    }
}