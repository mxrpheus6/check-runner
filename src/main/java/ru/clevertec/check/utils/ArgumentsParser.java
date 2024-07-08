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
    private String pathToFile = null;
    private String saveToFile = null;

    public ArgumentsParser(String[] args, ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        try {
            parseArgs(args);
        } catch (RuntimeException e) {
            exceptionHandler.handleException(e);
        }
    }

    public void parseArgs(String[] args) {
        for (String arg: args) {
            if (arg.startsWith("pathToFile=")) {
            parsePathToFile(arg);
            } else if (arg.startsWith("saveToFile=")) {
            parseSaveToFile(arg);
        }
        ResultCsvWriter writer = new ResultCsvWriter(saveToFile);
        exceptionHandler.setResultCsvWriter(writer);
        }
        try {
            validateArgPath();
            for (String arg : args) {
                if (arg.startsWith("discountCard=")) {
                    parseDiscountCard(arg);
                } else if (arg.startsWith("balanceDebitCard=")) {
                    parseBalanceDebitCard(arg);
                } else if (arg.contains("-")) {
                    parseIdQuantityPair(arg);
                }
            }
            validateArgBalance();
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

    public String getPathToFile() {
        return pathToFile;
    }

    public String getSaveToFile() {
        return saveToFile;
    }

    private void parseDiscountCard(String arg) {
        String discountCardNumber = arg.split("=")[1];
        if (discountCardNumber.length() != 4 || !NumberUtils.isNumeric(discountCardNumber)) {
            throw new InvalidArgumentException("Invalid discount card number");
        }
        Optional<DiscountCard> optionalDiscountCard = DiscountCardCsvRepository
                .findDiscountCardByNumber(discountCardNumber);
        discountCard = optionalDiscountCard.orElseGet(() -> new DiscountCardBuilder()
                .setNumber(discountCardNumber)
                .setDiscountAmount(2)
                .build());
    }

    private void parseBalanceDebitCard(String arg) {
        balanceDebitCard = Double.parseDouble(arg.split("=")[1]);
    }

    private void parseIdQuantityPair(String arg) {
        String[] parts = arg.split("-");
        if (parts.length == 2) {
            if (!NumberUtils.isInteger(parts[0]) || !NumberUtils.isInteger(parts[1])) {
                throw new InvalidArgumentException("Invalid id-quantity argument");
            }
            idQuantityPairs.add(arg);
        }
    }

    private void parsePathToFile(String arg) {
        pathToFile = arg.split("=")[1];
    }

    private void parseSaveToFile(String arg) {
        saveToFile = arg.split("=")[1];
    }

    private void validateArgBalance() {
        if (balanceDebitCard == null) {
            throw new InvalidArgumentException("Balance not provided");
        }
    }
    private void validateArgPath() {
        if (saveToFile == null && pathToFile == null) {
            throw new InvalidArgumentException("saveToFile and pathToFile are not provided");
        } else if (saveToFile == null) {
            throw new InvalidArgumentException("Save path not provided");
        } else if (pathToFile == null) {
            throw new InvalidArgumentException("Path to file not provided");
        }
    }
}
