import {Component} from "@angular/core";
import {UserSignUpDto} from "../../dto/user/UserSignUpDto";
import {Router} from '@angular/router';
import {NgForm} from "@angular/forms";
import {ResponseMessageDto} from "../../dto/reponsMessage/ResponseMessageDto";
import {UserService} from "../../services/user/UserService";
import {CurrencyService} from "../../services/currency/CurrencyService";
import {CurrencyDTO} from "../../dto/currency/CurrencyDTO";

/**
 * @author Timur Berezhnoi
 */
@Component({
    selector: "ifinrelax-signup",
    templateUrl: "./signUp.html",
    styleUrls: ["./notification.css"]
})
export default class SignUpComponent {

    private currencies: Array<CurrencyDTO>;
    private error: boolean = false;
    private responseMessages: Array<ResponseMessageDto>;

    constructor(private userService: UserService,
                private router: Router,
                private currencyService: CurrencyService) {
        this.fetchCurrencies();
    }

    private signUp(userForm: NgForm): void {
        if(userForm.valid) {
            this.userService.signUp(new UserSignUpDto(userForm.value.firstName, userForm.value.lastName, userForm.value.email, userForm.value.password, userForm.value.currency)).subscribe(
                response => {
                    this.router.navigate(["expenses"]);
                },
                error => {
                    this.error = true;
                    this.responseMessages = JSON.parse(error._body);
                }
            );
        }
    }

    private goToSignIn(event: Event): void {
        event.preventDefault();
        this.router.navigate(["signIn"]);
    }

    private fetchCurrencies(): void {
        this.currencyService.getCurrencies().subscribe(
            response => {
                this.currencies = response;
            },
            error => {
                this.error = true;
                this.responseMessages = JSON.parse(error._body);
            }
        );
    }
}