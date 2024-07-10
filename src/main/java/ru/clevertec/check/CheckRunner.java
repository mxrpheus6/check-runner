package ru.clevertec.check;

import ru.clevertec.check.exception.ExceptionHandler;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Order;
import ru.clevertec.check.model.OrderQuantity;
import ru.clevertec.check.model.builder.DiscountCardBuilder;
import ru.clevertec.check.model.builder.OrderBuilder;
import ru.clevertec.check.repository.DatabaseConnection;
import ru.clevertec.check.repository.DiscountCardRepository;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.utils.ArgumentsParser;
import ru.clevertec.check.utils.OrderQuantityParser;
import ru.clevertec.check.utils.ResultCsvWriter;

import java.util.List;

public class CheckRunner {
    public static void main(String[] args) {
        ExceptionHandler exceptionHandler = new ExceptionHandler();

        try {
            String saveToFile = ArgumentsParser.parseSaveToFile(args);
            exceptionHandler.setResultCsvWriter(new ResultCsvWriter(saveToFile));

            String url = ArgumentsParser.parseUrl(args);
            String username = ArgumentsParser.parseUsername(args);
            String password = ArgumentsParser.parsePassword(args);
            String discountCardNumber = ArgumentsParser.parseDiscountCard(args);
            Double balanceDebitCard = ArgumentsParser.parseBalanceDebitCard(args);
            List<String> idQuantityPairs = ArgumentsParser.parseIdQuantityPairs(args);

            DatabaseConnection databaseConnection = new DatabaseConnection(
                    url, username, password);
            ProductRepository productRepository = new ProductRepository(databaseConnection);
            DiscountCardRepository discountCardRepository = new DiscountCardRepository(
                    databaseConnection);

            OrderQuantityParser orderQuantityParser = new OrderQuantityParser(productRepository);
            List<OrderQuantity> orderQuantities = orderQuantityParser.parseOrderQuantities(idQuantityPairs);

            DiscountCard discountCard = null;
            if (discountCardNumber != null) {
                discountCard = discountCardRepository.findDiscountCardByNumber(discountCardNumber)
                        .orElse(new DiscountCardBuilder()
                                .setNumber(discountCardNumber)
                                .setDiscountAmount(2)
                                .build());
            }

            Order order = new OrderBuilder()
                    .setBalanceDebitCard(balanceDebitCard)
                    .setDiscountCard(discountCard)
                    .setOrderQuantities(orderQuantities)
                    .build();

            CheckService checkService = new CheckService();

            Check check = checkService.createCheck(order);
            ResultCsvWriter writer = new ResultCsvWriter(saveToFile);
            writer.setCheck(check);
            writer.writeResult();
        } catch (Exception e) {
            exceptionHandler.handleException(e);
        }
    }
}