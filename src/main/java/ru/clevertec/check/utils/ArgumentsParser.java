package main.java.ru.clevertec.check.utils;

import main.java.ru.clevertec.check.exception.ExceptionHandler;
import main.java.ru.clevertec.check.exception.InvalidArgumentException;
import main.java.ru.clevertec.check.model.DiscountCard;
import main.java.ru.clevertec.check.model.builder.DiscountCardBuilder;
import main.java.ru.clevertec.check.repository.DiscountCardCsvRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArgumentsParser {
    private final ExceptionHandler exceptionHandler;

    private final List<String> idQuantityPairs = new ArrayList<>();
    private DiscountCard discountCard = null;
    private Double balanceDebitCard = null;

    public ArgumentsParser(ExceptionHandler exceptionHandler, String[] args) {
        this.exceptionHandler = exceptionHandler;
        try {
            parseArgs(args);
        } catch (RuntimeException e) {
            exceptionHandler.handleException(e);
        }
    }

    public List<String> getIdQuantityPairs() {
        return idQuantityPairs;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public Double getBalanceDebitCard() {
        return balanceDebitCard;
    }

    private void parseArgs(String[] args) {
        for (String arg: args) {
            if (arg.startsWith("discountCard=")) {
                String discountCardNumber = arg.split("=")[1];
                if (discountCardNumber.length() != 4 || !NumberUtils.isNumeric(discountCardNumber)) {
                    throw new InvalidArgumentException("Invalid discount card number");
                }
                Optional<DiscountCard> optionalDiscountCard =
                        DiscountCardCsvRepository.findDiscountCardByNumber(discountCardNumber);
                discountCard = optionalDiscountCard
                        .orElseGet(() -> new DiscountCardBuilder()
                        .setNumber(discountCardNumber)
                        .setDiscountAmount(2)
                        .build());
            } else if (arg.startsWith("balanceDebitCard=")) {
                balanceDebitCard = Double.parseDouble(arg.split("=")[1]);
            } else if (arg.contains("-")) {
                String[] parts = arg.split("-");
                if (parts.length == 2) {
                    if (!NumberUtils.isInteger(parts[0]) || !NumberUtils.isInteger(parts[1])) {
                        throw new InvalidArgumentException("Invalid iq-quantity argument");
                    }
                    idQuantityPairs.add(arg);
                }
            }
        }
        if (balanceDebitCard == null) {
            throw new InvalidArgumentException("Balance not provided");
        }
    }
}
