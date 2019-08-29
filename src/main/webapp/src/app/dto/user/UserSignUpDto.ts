import {CurrencyDTO} from "../currency/CurrencyDTO";

/**
 * @author Timur Berezhnoi
 */
export class UserSignUpDto {
    constructor(private firstName: string,
                private lastName: string,
                private email: string,
                private password: string,
                private currency: CurrencyDTO) {}
}