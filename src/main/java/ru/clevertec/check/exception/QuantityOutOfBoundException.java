package main.java.ru.clevertec.check.exception;

public class QuantityOutOfBoundException extends RuntimeException {
    public QuantityOutOfBoundException(String message) {
        super(message);
    }
}
