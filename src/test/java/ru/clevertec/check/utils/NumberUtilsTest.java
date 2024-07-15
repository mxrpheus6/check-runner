package ru.clevertec.check.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberUtilsTest {
    @Test
    void isNumeric_true() {
        boolean result = NumberUtils.isNumeric("123");
        Assertions.assertTrue(result);
    }

    @Test
    void isNumeric_false() {
        boolean result = NumberUtils.isNumeric("123abc");
        Assertions.assertFalse(result);
    }

    @Test
    void isInteger_true() {
        boolean result = NumberUtils.isInteger("52");
        Assertions.assertTrue(result);
    }

    @Test
    void isInteger_false() {
        boolean result = NumberUtils.isInteger("5.2");
        Assertions.assertFalse(result);
    }

    @Test
    void isDecimal_true() {
        boolean result = NumberUtils.isDecimal("5.2");
        Assertions.assertTrue(result);
    }

    @Test
    void isDecimal_false() {
        boolean result = NumberUtils.isDecimal("52");
        Assertions.assertFalse(result);
    }

    @Test
    void floorToHundreds() {
        Double result = NumberUtils.floorToHundredths(1.236);
        Assertions.assertEquals(1.23, result);
    }
}
