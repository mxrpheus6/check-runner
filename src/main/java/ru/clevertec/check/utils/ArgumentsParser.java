package main.java.ru.clevertec.check.utils;

import main.java.ru.clevertec.check.exception.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class ArgumentsParser {
    public static final String ARG_DISCOUNT_CARD = "discountCard=";
    public static final String ARG_BALANCE_DEBIT_CARD = "balanceDebitCard=";
    public static final String ARG_SAVE_TO_FILE = "saveToFile=";
    public static final String ARG_PATH_TO_FILE = "pathToFile=";

    private static String parseArgument(String[] args, String variable) {
        for (String arg: args) {
            if (arg.startsWith(variable)) {
                return arg.split("=")[1];
            }
        }
        return null;
    }

    public static List<String> parseIdQuantityPairs(String[] args) {
        List<String> idQuantityPairs = new ArrayList<>();
        for (String arg: args) {
            if (arg.contains("-")) {
                String[] parts = arg.split("-");
                if (parts.length == 2) {
                    if (!NumberUtils.isInteger(parts[0]) || !NumberUtils.isInteger(parts[1])) {
                        throw new InvalidArgumentException("Invalid id-quantity argument");
                    }
                    idQuantityPairs.add(arg);
                }
            }
        }
        return idQuantityPairs;
    }

    public static Double parseBalanceDebitCard(String[] args) {
        String balanceStr = parseArgument(args, ARG_BALANCE_DEBIT_CARD);
        if (balanceStr == null) {
            throw new InvalidArgumentException("Balance not provided");
        }
        return Double.parseDouble(balanceStr);
    }

    public static String parseDiscountCard(String[] args) {
        String discountCardNumber = parseArgument(args, ARG_DISCOUNT_CARD);
        if (discountCardNumber != null &&
                (discountCardNumber.length() != 4 || !NumberUtils.isNumeric(discountCardNumber))) {
            throw new InvalidArgumentException("Invalid discount card number");
        }

        return discountCardNumber;
    }

    public static String parsePathToFile(String[] args) {
        String pathToFile = parseArgument(args, ARG_PATH_TO_FILE);
        if (pathToFile == null) {
            throw new InvalidArgumentException("Path to file not provided");
        }
        return pathToFile;
    }

    public static String parseSaveToFile(String[] args) {
        String saveToFile = parseArgument(args, ARG_SAVE_TO_FILE);
        if (saveToFile == null) {
            throw new InvalidArgumentException("Save path not provided");
        }
        return saveToFile;
    }
}
