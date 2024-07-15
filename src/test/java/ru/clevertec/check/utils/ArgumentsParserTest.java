package ru.clevertec.check.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.exception.InvalidArgumentException;

import java.util.List;

class ArgumentsParserTest {

    private final String[] validArgs = {"3-1", "discountCard=4444", "balanceDebitCard=100.50",
            "saveToFile=./my_result.csv", "datasource.url=jdbc:postgresql://localhost:5432/check",
            "datasource.username=postgres", "datasource.password=postgres"};

    private final String[] invalidArgs = {"3-a", "balanceDebitCard=100.5a", "discountCard=444"};

    @Test
    void parseIdQuantityPairs() {
        List<String> result = ArgumentsParser.parseIdQuantityPairs(validArgs);
        Assertions.assertEquals("3-1", result.getFirst());
    }

    @Test
    void parseIdQuantityPairs_throwsInvalidArgumentException() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> ArgumentsParser.parseIdQuantityPairs(invalidArgs));
    }

    @Test
    void parseBalanceDebitCard() {
        Double result = ArgumentsParser.parseBalanceDebitCard(validArgs);
        Assertions.assertEquals(100.50, result);
    }

    @Test
    void parseBalanceDebitCard_throwsInvalidArgumentException() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> ArgumentsParser.parseBalanceDebitCard(invalidArgs));
    }

    @Test
    void parseDiscountCard() {
        String result = ArgumentsParser.parseDiscountCard(validArgs);
        Assertions.assertEquals("4444", result);
    }

    @Test
    void parseDiscountCard_throwsInvalidArgumentException() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> ArgumentsParser.parseDiscountCard(invalidArgs));
    }

    @Test
    void parseUrl() {
        String result = ArgumentsParser.parseUrl(validArgs);
        Assertions.assertEquals("jdbc:postgresql://localhost:5432/check", result);
    }

    @Test
    void parseUrl_throwsInvalidArgumentException() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> ArgumentsParser.parseUrl(invalidArgs));
    }

    @Test
    void parseUsername() {
        String result = ArgumentsParser.parseUsername(validArgs);
        Assertions.assertEquals("postgres", result);
    }

    @Test
    void parseUsername_throwsInvalidArgumentException() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> ArgumentsParser.parseUsername(invalidArgs));
    }

    @Test
    void parsePassword() {
        String result = ArgumentsParser.parsePassword(validArgs);
        Assertions.assertEquals("postgres", result);
    }

    @Test
    void parsePassword_throwsInvalidArgumentException() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> ArgumentsParser.parsePassword(invalidArgs));
    }

    @Test
    void parseSaveToFile() {
        String result = ArgumentsParser.parseSaveToFile(validArgs);
        Assertions.assertEquals("./my_result.csv", result);
    }

    @Test
    void parseSaveToFile_throwsInvalidArgumentException() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> ArgumentsParser.parseSaveToFile(invalidArgs));
    }
}
