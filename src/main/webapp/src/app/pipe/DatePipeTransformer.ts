import {Pipe, PipeTransform} from "@angular/core";
import {ExpenseDTO} from "../dto/expense/ExpenseDTO";
import {DateOrder} from "./DateOrder";

/**
 * @author Timur Berezhnoi
 */
@Pipe({
    name: "orderByDate"
})
export class DatePipeTransformer implements PipeTransform {

    /**
     * Sort array by date of creation.
     *
     * @param value
     * @param args
     * @returns {Array<ExpenseDTO>}
     */
    transform(value: Array<DateOrder>, args?: string[]): any {
        if(!value) {
            return value;
        }

        return value.sort((v1: DateOrder, v2: DateOrder) => {
            let firstDay: number = new Date(v1.createdAt).getDate();
            let secondDay: number = new Date(v2.createdAt).getDate();

            return secondDay - firstDay;
        });
    }
}