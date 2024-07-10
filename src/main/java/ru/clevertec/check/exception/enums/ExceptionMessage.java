package ru.clevertec.check.exception.enums;

public enum ExceptionMessage {
    BAD_REQUEST("ERROR\nBAD REQUEST\n"),
    NOT_ENOUGH_MONEY("ERROR\nNOT ENOUGH MONEY\n"),
    INTERNAL_SERVER_ERROR("ERROR\nINTERNAL SERVER ERROR\n");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
