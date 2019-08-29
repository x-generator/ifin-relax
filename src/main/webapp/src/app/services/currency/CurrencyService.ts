import {Http, Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {CurrencyDTO} from "../../dto/currency/CurrencyDTO";
import "rxjs/add/operator/map";

/**
 * @author Timur Berezhnoi
 */
@Injectable()
export class CurrencyService {

    private readonly CURRENCIES: string = "/currencies";

    constructor(private http: Http) {}

    /**
     * Fetch all available currencies.
     *
     * @returns Observable<Array<CurrencyDTO>>
     */
    public getCurrencies(): Observable<Array<CurrencyDTO>> {
        return this.http.get(this.CURRENCIES).map((response: Response) => response.json());
    }
}