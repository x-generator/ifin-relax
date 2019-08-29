/**
 * @author Timur Berezhnoi
 */
export class ResponseMessageDto {
    constructor(private message: string) {}

    public setMessage(message: string): void {
        this.message = message;
    }

    public getMessage(): string {
        return this.message;
    }
}