package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.exception.ExceptionHandler;
import main.java.ru.clevertec.check.model.Check;
import main.java.ru.clevertec.check.model.DiscountCard;
import main.java.ru.clevertec.check.model.Order;
import main.java.ru.clevertec.check.model.OrderQuantity;
import main.java.ru.clevertec.check.model.builder.DiscountCardBuilder;
import main.java.ru.clevertec.check.model.builder.OrderBuilder;
import main.java.ru.clevertec.check.repository.DiscountCardCsvRepository;
import main.java.ru.clevertec.check.repository.ProductCsvRepository;
import main.java.ru.clevertec.check.service.CheckService;
import main.java.ru.clevertec.check.utils.ArgumentsParser;
import main.java.ru.clevertec.check.utils.OrderQuantityParser;
import main.java.ru.clevertec.check.utils.ResultCsvWriter;

import java.util.List;

public class CheckRunner {
    public static void main(String[] args) {
        ExceptionHandler exceptionHandler = new ExceptionHandler();

        try {
            List<String> idQuantityPairs = ArgumentsParser.parseIdQuantityPairs(args);
            String discountCardNumber = ArgumentsParser.parseDiscountCard(args);
            Double balanceDebitCard = ArgumentsParser.parseBalanceDebitCard(args);

            ProductCsvRepository productCsvRepository = new ProductCsvRepository();
            DiscountCardCsvRepository discountCardCsvRepository = new DiscountCardCsvRepository();

            OrderQuantityParser orderQuantityParser = new OrderQuantityParser(productCsvRepository);
            List<OrderQuantity> orderQuantities = orderQuantityParser.parseOrderQuantities(idQuantityPairs);

            DiscountCard discountCard = null;
            if (discountCardNumber != null) {
                discountCard = discountCardCsvRepository.findDiscountCardByNumber(discountCardNumber)
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
            ResultCsvWriter writer = new ResultCsvWriter(check);
            writer.writeResult();
        } catch (RuntimeException e) {
            exceptionHandler.handleException(e);
        }
    }
}
