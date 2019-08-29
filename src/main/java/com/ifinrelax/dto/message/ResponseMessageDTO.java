package com.ifinrelax.dto.message;

/**
 * Immutable holder for response message.
 *
 * @author Timur Berezhnoi
 */
public final class ResponseMessageDTO {

    private final String message;

    /**
     * Creates a ResponseMessageDTO detailde message.
     *
     * @param message is a detailed message.
     */
    public ResponseMessageDTO(final String message) {
        this.message = message;
    }

    /**
     * @return current response message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseMessageDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}