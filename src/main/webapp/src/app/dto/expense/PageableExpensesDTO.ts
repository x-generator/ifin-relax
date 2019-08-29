import {ExpenseDTO} from "./ExpenseDTO";

/**
 * @author Tinur Berezhnoi
 */
export class PageableExpensesDTO {
    constructor(public content: Array<ExpenseDTO>,
                public first: boolean,
                public last: boolean,
                public number: number,
                public numberOfElements: number,
                public size: number,
                public sort: any,
                public totalElements: number,
                public totalPages: number) {}
}