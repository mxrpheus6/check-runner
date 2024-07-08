package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.exception.ExceptionHandler;
import main.java.ru.clevertec.check.model.Check;
import main.java.ru.clevertec.check.model.Order;
import main.java.ru.clevertec.check.model.builder.OrderBuilder;
import main.java.ru.clevertec.check.repository.ProductCsvRepository;
import main.java.ru.clevertec.check.service.CheckService;
import main.java.ru.clevertec.check.utils.ArgumentsParser;
import main.java.ru.clevertec.check.utils.OrderQuantityParser;
import main.java.ru.clevertec.check.utils.ResultCsvWriter;

public class CheckRunner {
    public static void main(String[] args) {
        ExceptionHandler exceptionHandler = new ExceptionHandler();

        ArgumentsParser argumentsParser = new ArgumentsParser(args, exceptionHandler);

        ProductCsvRepository productCsvRepository = new ProductCsvRepository(argumentsParser.getPathToFile());

        OrderQuantityParser orderQuantityParser = new OrderQuantityParser(
                exceptionHandler,
                productCsvRepository,
                argumentsParser.getIdQuantityPairs());

        Order order = new OrderBuilder()
                .setBalanceDebitCard(argumentsParser.getBalanceDebitCard())
                .setDiscountCard(argumentsParser.getDiscountCard())
                .setOrderQuantities(orderQuantityParser.getOrderQuantities())
                .build();

        CheckService checkService = new CheckService();
        try {
            Check check = checkService.createCheck(order);
            ResultCsvWriter writer = new ResultCsvWriter(argumentsParser.getSaveToFile());
            writer.setCheck(check);
            writer.writeResult();
        } catch (RuntimeException e) {
            exceptionHandler.handleException(e);
        }

    }
}