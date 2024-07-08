package main.java.ru.clevertec.check.exception;

import main.java.ru.clevertec.check.exception.enums.ExceptionMessage;
import main.java.ru.clevertec.check.utils.ResultCsvWriter;

public class ExceptionHandler {
    private final Integer EXIT_FAILURE = 1;
    private ResultCsvWriter resultCsvWriter;

    public void setResultCsvWriter(ResultCsvWriter resultCsvWriter) {
        this.resultCsvWriter = resultCsvWriter;
    }

    public void handleException(RuntimeException e) {
        if (e instanceof InvalidArgumentException ||
                e instanceof QuantityOutOfBoundException ||
                e instanceof ProductNotFoundException ||
                e instanceof NumberFormatException) {
            resultCsvWriter.writeError(ExceptionMessage.BAD_REQUEST.getMessage() + e.getMessage());
        } else if (e instanceof NotEnoughMoneyException) {
            resultCsvWriter.writeError(ExceptionMessage.NOT_ENOUGH_MONEY.getMessage());
        } else {
            resultCsvWriter.writeError(ExceptionMessage.INTERNAL_SERVER_ERROR.getMessage());
        }
        System.exit(EXIT_FAILURE);
    }
}