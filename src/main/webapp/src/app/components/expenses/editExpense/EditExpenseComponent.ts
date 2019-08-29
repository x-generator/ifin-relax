import {Component, OnInit} from "@angular/core";
import {NgForm} from "@angular/forms";
import {NgbActiveModal, NgbDatepickerConfig, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {ExpenseDTO} from "../../../dto/expense/ExpenseDTO";
import {DatePipeTransformer} from "../../../pipe/DatePipeTransformer";
import {ExpenseService} from "../../../services/expense/ExpenseService";

/**
 * @author Timur Berezhnoi
 */
@Component({
    selector: "add-expense",
    templateUrl: "./editExpense.html",
    styleUrls: ["../newExpense/newExpense.css"]
})
export default class EditExpenseComponent implements OnInit {

    private modalTitle: string;
    private expense: ExpenseDTO;
    private expenses: Array<ExpenseDTO>;
    private userCurrency: string;
    private model: NgbDateStruct;

    constructor(private activeModal: NgbActiveModal,
                private datePipeTransformer: DatePipeTransformer,
                private datePicketConf: NgbDatepickerConfig,
                private expenseService: ExpenseService
    ) {
        this.configDatePicker();
    }

    ngOnInit(): void {
        let date: Date = new Date(this.expense.createdAt);
        this.model = {year: date.getUTCFullYear(), month: date.getUTCMonth() + 1, day: date.getDate()};
    }

    public addExpense(expenseForm: NgForm): void {
        if(expenseForm && expenseForm.valid) {
            let centAmount: number = expenseForm.value.amount * 100;
            let createdAt: string = new Date(expenseForm.value.datePicker.year, expenseForm.value.datePicker.month - 1, expenseForm.value.datePicker.day).toLocaleDateString();
            let expenseDto: ExpenseDTO = new ExpenseDTO(centAmount, expenseForm.value.object, createdAt);

            this.expenseService.addExpense(expenseDto).subscribe(
                response => {
                    this.expenses.push(expenseDto);
                    this.expenses = this.datePipeTransformer.transform(this.expenses);
                    this.activeModal.close(centAmount);
                },
                error => {
                    console.log(error);
                });
        }
    }

    private configDatePicker(): void {
        let now: Date = new Date();
        this.datePicketConf.maxDate = {year: now.getUTCFullYear(), month: now.getUTCMonth() + 1, day: now.getUTCDate()};
        this.datePicketConf.minDate = {year: 1970, month: 1, day: 1};
    }
}