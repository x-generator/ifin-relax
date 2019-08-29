import {Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {UserService} from "../user/UserService";
import "rxjs/add/operator/map";

/**
 * The guard is suppose to determin if user is logged in.
 *
 * @author Timur Berezhnoi
 */
@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router, private userService: UserService) {}

    public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
        return this.userService.isAuthencticated().map((response: Response) => {
            if(state.url !== "/signIn" && state.url !== "/signUp") {
                if(response.json().isAuthenticated) {
                    return true;
                } else {
                    this.router.navigate(["/signIn"]);
                    return false;
                }
            } else {
                // Signed user acces to login or sign up page.
                if(response.json().isAuthenticated) {
                    this.router.navigate(["/expenses"]);
                    return true;
                } else {
                    return true;
                }
            }
        });
    }
}