import {DateOrder} from "../../pipe/DateOrder";

/**
 * @author Timur Berezhnoi
 */
export class ExpenseDTO implements DateOrder {

    /**
     * ExpenseDTO constructor.
     *
     * @param amount
     * @param object
     * @param createdAt
     * @param id - generated from server and not required in froned part
     */
    constructor(public amount: number,
                public object: string,
                public createdAt: string,
                public id?: number) {

    }
}