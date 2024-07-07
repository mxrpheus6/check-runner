package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.exception.ExceptionHandler;
import main.java.ru.clevertec.check.model.Check;
import main.java.ru.clevertec.check.model.Order;
import main.java.ru.clevertec.check.model.builder.OrderBuilder;
import main.java.ru.clevertec.check.service.CheckService;
import main.java.ru.clevertec.check.utils.ArgumentsParser;
import main.java.ru.clevertec.check.utils.OrderQuantityParser;
import main.java.ru.clevertec.check.utils.ResultCsvWriter;

public class CheckRunner {
    public static void main(String[] args) {
        ExceptionHandler exceptionHandler = new ExceptionHandler();

        ArgumentsParser argumentsParser = new ArgumentsParser(exceptionHandler, args);
        OrderQuantityParser orderQuantityParser = new OrderQuantityParser(
                exceptionHandler, argumentsParser.getIdQuantityPairs());

        Order order = new OrderBuilder()
                .setBalanceDebitCard(argumentsParser.getBalanceDebitCard())
                .setDiscountCard(argumentsParser.getDiscountCard())
                .setOrderQuantities(orderQuantityParser.getOrderQuantities())
                .build();

        CheckService checkService = new CheckService();
        try {
            Check check = checkService.createCheck(order);
            ResultCsvWriter writer = new ResultCsvWriter(check);
            writer.writeResult();
        } catch (RuntimeException e) {
            exceptionHandler.handleException(e);
        }

    }
}
