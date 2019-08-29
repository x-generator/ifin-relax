import {Injectable} from "@angular/core";

/**
 * @author Timur Berezhnoi
 */
@Injectable()
export class CookieService {

    public getCookie(cookieName: string): string {
        return document.cookie.split(";").reduce(function(prev, cookie) {
            let cookies = cookie.split("=");
            return (cookies[0].trim() === cookieName) ? cookies[1] : prev;
        }, undefined);
    }
}