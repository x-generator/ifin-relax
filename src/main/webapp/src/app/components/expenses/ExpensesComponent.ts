import {Component} from "@angular/core";
import {NgForm} from "@angular/forms";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {ExpenseDTO} from "../../dto/expense/ExpenseDTO";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {ExpenseService} from "../../services/expense/ExpenseService";
import {CookieService} from "../../services/cookie/CookieService";
import ModalExpenseComponent from "./modal/ModalExpenseComponent";
import {DatePipeTransformer} from "../../pipe/DatePipeTransformer";

/**
 * @author Timur Berezhnoi
 */
@Component({
    selector: "expenses-component",
    templateUrl: "./expenses.html"
})
export default class ExpensesComponent {
    private expenses: Array<ExpenseDTO>;
    private total: number = 0;
    private userCurrency: string;
    private page: number = 1;
    private totalPages: number;

    constructor(private expenseService: ExpenseService,
                private cookieService: CookieService,
                private modalService: NgbModal,
                private datePipeTransformer: DatePipeTransformer) {
        this.userCurrency = cookieService.getCookie("userCurrency");
        this.fetchUserExpenses(0);
    }

    /**
     * The method is suposed to show expense creation form.
     */
    private openExpenseCreationForm(event: Event) {
        event.preventDefault();
        const modalRef: NgbModalRef = this.modalService.open(ModalExpenseComponent, {size: "lg"});
        modalRef.componentInstance.modalTitle = "New expense";
        modalRef.componentInstance.userCurrency = this.userCurrency;
        modalRef.componentInstance.expenses = this.expenses;
        modalRef.componentInstance.saveAction = this.addExpense;

        modalRef.result.then(
            (amount: number) => {
                this.total += amount;
                if(this.expenses.length > 10) {
                    this.page = ++this.totalPages;
                }
            }, (reason) => {
                console.log(reason);
            });
    }

    private openExpenseEditForm(event: Event, expenseDto: ExpenseDTO): void {
        event.preventDefault();
        const modalRef: NgbModalRef = this.modalService.open(ModalExpenseComponent, {size: "lg"});
        modalRef.componentInstance.modalTitle = "Edit expense";
        modalRef.componentInstance.expense = expenseDto;
        modalRef.componentInstance.userCurrency = this.userCurrency;
        modalRef.componentInstance.saveAction = this.updateExpense;

        modalRef.result.then(
            (updatedExpenseDto: ExpenseDTO) => {
                this.expenses[this.expenses.indexOf(expenseDto)] = updatedExpenseDto;
            }, (reason) => {
                console.log(reason);
            });
    }

    /**
     * This function is declared as arrow function, because the function
     * will be passed as an function reference to anothe component and in this case
     * we need to save lexical context of `this`.
     *
     * @param {NgForm} expenseForm
     * @param {NgbActiveModal} activeModal
     */
    private addExpense = (expenseForm: NgForm, activeModal: NgbActiveModal): void => {
        if(expenseForm && expenseForm.valid) {
            let centAmount: number = expenseForm.value.amount * 100;
            let createdAt: string = new Date(expenseForm.value.datePicker.year, expenseForm.value.datePicker.month - 1, expenseForm.value.datePicker.day).toLocaleDateString();
            let expenseDto: ExpenseDTO = new ExpenseDTO(centAmount, expenseForm.value.object, createdAt);

            this.expenseService.addExpense(expenseDto).subscribe(
                response => {
                    this.expenses.push(expenseDto);
                    this.expenses = this.datePipeTransformer.transform(this.expenses);
                    activeModal.close(centAmount);
                }, error => {
                    console.log(error);
                });
        }
    };

    /**
     * This function is declared as arrow function, because the function
     * will be passed as an function reference to anothe component and in this case
     * we need to save lexical context of `this`.
     *
     * @param {NgForm} expenseForm
     * @param acteiveModal
     */
    private updateExpense = (expenseForm: NgForm, acteiveModal: NgbActiveModal): void => {
        if(expenseForm && expenseForm.valid) {
            let centAmount: number = expenseForm.value.amount * 100;
            let createdAt: string = new Date(expenseForm.value.datePicker.year, expenseForm.value.datePicker.month - 1, expenseForm.value.datePicker.day).toLocaleDateString();
            let expenseDto: ExpenseDTO = new ExpenseDTO(centAmount, expenseForm.value.object, createdAt, expenseForm.value.expenseId);

            this.expenseService.updateExpense(expenseDto).subscribe(
                response => {
                    acteiveModal.close(expenseDto);
                }, error => {
                    console.log(error);
                });
        }
    };

    public deleteExpense(event: Event, expenseDto: ExpenseDTO): void {
        event.preventDefault();
        this.expenseService.deleteExpense(expenseDto).subscribe(
            response => {
                this.total = this.total - expenseDto.amount;
                this.expenses.splice(this.expenses.indexOf(expenseDto), 1);
            },
            error => {
                console.log(error);
            }
        );
    }

    private fetchUserExpenses(pageNumber: number): void {
        this.expenseService.getUserExpenses(pageNumber).subscribe(
            response => {
                if(response.expensesPage.totalPages === 0) {
                    this.totalPages = 1;
                } else {
                    this.totalPages = response.expensesPage.totalPages * 10;
                }
                this.expenses = response.expensesPage.content;
                this.total = response.totalAmount;
            }, error => {
                console.log(error);
            }
        );
    }

    private pageChanged(pageNumber: number): void {
        this.fetchUserExpenses(pageNumber - 1);
    }
};