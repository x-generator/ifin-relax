/**
 * @author Timur Berezhnoi
 */
export class UserDto {
    constructor(private id?: number,
                private firstName?: string,
                private lastName?: string,
                private email?: string,
                private roles?: Array<Object>) {}
}