import {Http, Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {UserSignUpDto} from "../../dto/user/UserSignUpDto";
import {UserSignInDto} from "../../dto/user/UserSignInDto";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/map";

/**
 * @author Timur Berezhnoi
 */
@Injectable()
export class UserService {

    private readonly SIGNUP_END_POUNT: string = "/signUp";
    private readonly SIGNIN_END_POUNT: string = "/signIn";
    private readonly SIGN_OUT_END_POINT: string = "/signOut";
    private readonly IS_AUTHENTICATED: string = "/isUserAuthenticated";

    constructor(private http: Http) {}

    public signUp(userDto: UserSignUpDto): Observable<Object> {
        return this.http.post(this.SIGNUP_END_POUNT, userDto).map((response: Response) => response.json());
    }

    public signIn(userDto: UserSignInDto): Observable<Object> {
        let signInUrl: string = this.SIGNIN_END_POUNT + "?email=" + userDto.getEmail() + "&password=" + userDto.getPassword() + "&rememberMe=true";
        return this.http.post(signInUrl, {}).map((response: Response) => {return response});
    }

    public signOut(): Observable<Object> {
        let signInUrl: string = this.SIGN_OUT_END_POINT;
        return this.http.get(signInUrl, {}).map((response: Response) => {return response});
    }

    /**
     * Check if user is authenticated.
     *
     * @returns {Observable<Response>}
     */
    public isAuthencticated(): Observable<Response> {
        return this.http.get(this.IS_AUTHENTICATED);
    }
}