import {Component} from "@angular/core";
import {UserSignInDto} from "../../dto/user/UserSignInDto";
import {Router} from '@angular/router';
import {NgForm} from "@angular/forms";
import {UserService} from "../../services/user/UserService";
import {ResponseMessageDto} from "../../dto/reponsMessage/ResponseMessageDto";

/**
 * @author Timur Berezhnoi
 */
@Component({
    selector: "ifinrelax-signin",
    templateUrl: "./signIn.html",
    styleUrls: ["./notification.css"]
})
export default class SignInComponent {
    private userService: UserService;
    private router: Router;
    private error: boolean = false;
    private responseMessage: ResponseMessageDto;

    constructor(userService: UserService, router: Router) {
        this.userService = userService;
        this.router = router;
    }

    private signIn(userForm: NgForm): void {
        if(userForm.valid) {
            this.userService.signIn(new UserSignInDto(userForm.value.email, userForm.value.password)).subscribe(
                response => {
                    this.router.navigate(["expenses"]);
                },
                error => {
                    this.error = true;
                    this.responseMessage = JSON.parse(error._body);
                }
            );
        }
    }

    private goToSignUp(event: Event): void {
        event.preventDefault();
        this.router.navigate(["signUp"]);
    }
}