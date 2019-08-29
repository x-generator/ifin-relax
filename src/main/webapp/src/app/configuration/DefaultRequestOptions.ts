import {Headers, BaseRequestOptions} from "@angular/http";
import {Injectable} from "@angular/core";

/**
 * @author Timur Berezhnoi
 */
@Injectable()
export class DefaultRequestOptions extends BaseRequestOptions {
    headers: Headers = new Headers({
        "Accept": "application/json"
    });
}