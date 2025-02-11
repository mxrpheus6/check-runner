package main.java.ru.clevertec.check.exception;

import main.java.ru.clevertec.check.exception.enums.ExceptionMessage;
import main.java.ru.clevertec.check.utils.ResultCsvWriter;

public class ExceptionHandler {

    public void handleException(RuntimeException e) {
        if (e instanceof InvalidArgumentException ||
                e instanceof QuantityOutOfBoundException ||
                e instanceof ProductNotFoundException ||
                e instanceof NumberFormatException) {
            ResultCsvWriter.writeError(ExceptionMessage.BAD_REQUEST.getMessage() + e.getMessage());
        } else if (e instanceof NotEnoughMoneyException) {
            ResultCsvWriter.writeError(ExceptionMessage.NOT_ENOUGH_MONEY.getMessage());
        } else {
            ResultCsvWriter.writeError(ExceptionMessage.INTERNAL_SERVER_ERROR.getMessage());
        }
        System.exit(1);
    }
}
