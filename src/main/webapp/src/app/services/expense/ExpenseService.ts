import {Http, Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {ExpensesDTO} from "../../dto/expense/ExpensesDTO";
import {ExpenseDTO} from "../../dto/expense/ExpenseDTO";
import "rxjs/add/operator/map";

/**
 * @author Timur Berezhnoi
 */
@Injectable()
export class ExpenseService {

    private readonly USER_EXPENSES: string = "/expenses";
    private readonly EXPENSE: string = "/expense";
    private readonly EXPENSE_STATISTIC: string = "/expenseStatistic";
    private readonly EXPENSE_STATISTIC_YEARS: string = "/expenseStatisticYears";

    constructor(private http: Http) {}

    /**
     * Fetch expenses for logged in user.
     *
     * @returns Observable<Array<PageableExpensesDTO>>
     */
    public getUserExpenses(page: number): Observable<ExpensesDTO> {
        return this.http.get(this.USER_EXPENSES + "?page=" + page + "&size=10").map((response: Response) => response.json());
    }

    /**
     * Save expense.
     *
     * @param expenseDto
     * @returns {Observable<Object>}
     */
    public addExpense(expenseDto: ExpenseDTO): Observable<Object>  {
        return this.http.post(this.EXPENSE, expenseDto).map((response: Response) => response);
    }

    /**
     * Get expenses statistic.
     *
     * @param year - the yer of statisic
     * @returns {Observable<Object>}
     */
    public getExpensesStatisticForYear(year: number): Observable<Array<any>>  {
        return this.http.get(this.EXPENSE_STATISTIC + "/" + year).map((response: Response) => response.json());
    }

    /**
     * Get expensess statistic available years.
     *
     * @returns Observable<Array<string>>
     */
    public getExpensesStatisticYears(): Observable<Array<number>> {
        return this.http.get(this.EXPENSE_STATISTIC_YEARS).map((response: Response) => response.json());
    }

    public deleteExpense(expense: ExpenseDTO): Observable<Response> {
        return this.http.delete(this.EXPENSE + `/${expense.id}`).map((response: Response) => response);
    }

    /**
     * Updates an expense.
     *
     * @param {ExpenseDTO} expenseDto
     * @returns {Observable<Object>}
     */
    public updateExpense(expenseDto: ExpenseDTO): Observable<Object> {
        return this.http.patch(this.EXPENSE, expenseDto).map((response: Response) => response);
    }
}