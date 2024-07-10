package ru.clevertec.check.utils;

public class NumberUtils {
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("-?\\d+");
    }

    public static boolean isDecimal(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("-?\\d+\\.\\d+");
    }

    public static double floorToHundredths(double number) {
        return Math.floor(number * 100.0) / 100.0;
    }
}
