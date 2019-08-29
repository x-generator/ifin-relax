import {PageableExpensesDTO} from "./PageableExpensesDTO";

/**
 * @author Tinur Berezhnoi
 */
export class ExpensesDTO {
    constructor(public expensesPage: PageableExpensesDTO, public totalAmount: number) {}
}