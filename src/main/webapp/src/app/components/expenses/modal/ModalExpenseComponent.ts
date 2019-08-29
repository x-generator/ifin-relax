import {Component, OnInit} from "@angular/core";
import {NgForm} from "@angular/forms";
import {NgbActiveModal, NgbDatepickerConfig, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {ExpenseDTO} from "../../../dto/expense/ExpenseDTO";

/**
 * @author Timur Berezhnoi
 */
@Component({
    selector: "modal-expense",
    templateUrl: "./modalExpense.html",
    styleUrls: ["./modalExpense.css"]
})
export default class ModalExpenseComponent implements OnInit {

    private modalTitle: string;
    private expense: ExpenseDTO;
    private expenses: Array<ExpenseDTO>;
    private userCurrency: string;
    private model: NgbDateStruct;
    private saveAction: Function;

    constructor(private activeModal: NgbActiveModal, private datePicketConf: NgbDatepickerConfig) {
        this.configDatePicker();
    }

    ngOnInit(): void {
        this.setExpenseCreatedAt();
    }

    public onSaveAction(expenseForm: NgForm): void {
        this.saveAction(expenseForm, this.activeModal);
    }

    private configDatePicker(): void {
        let now: Date = new Date();
        this.datePicketConf.maxDate = {year: now.getUTCFullYear(), month: now.getUTCMonth() + 1, day: now.getUTCDate()};
        this.datePicketConf.minDate = {year: 1970, month: 1, day: 1};
    }

    /**
     * This function sets expens created at date if this modal is used for updating.
     */
    private setExpenseCreatedAt(): void {
        if(this.expense) {
            let date: Date = new Date(this.expense.createdAt);
            const monthOffset = 1;
            this.model = {year: date.getUTCFullYear(), month: date.getMonth() + monthOffset, day: date.getDate()};
        }
    }
}