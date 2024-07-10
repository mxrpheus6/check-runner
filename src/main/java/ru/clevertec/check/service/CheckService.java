package ru.clevertec.check.service;

import ru.clevertec.check.exception.NotEnoughMoneyException;
import ru.clevertec.check.exception.QuantityOutOfBoundException;
import ru.clevertec.check.model.*;
import ru.clevertec.check.model.builder.CheckBuilder;
import ru.clevertec.check.model.builder.CheckItemBuilder;
import ru.clevertec.check.utils.NumberUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CheckService {
    public Check createCheck(Order order) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        List<CheckItem> items = initializeCheckItems(order);

        Double totalPrice = calculateTotalPrice(items);
        Double totalDiscountAmount = calculateDiscountAmount(items);
        Double totalWithDiscount = totalPrice - totalDiscountAmount;

        if (totalWithDiscount > order.getBalanceDebitCard()) {
            throw new NotEnoughMoneyException("Not enough money");
        }

        return new CheckBuilder()
                .setDate(date)
                .setTime(time)
                .setDiscountCard(order.getDiscountCard())
                .setItems(items)
                .setTotalPrice(totalPrice)
                .setTotalDiscountAmount(totalDiscountAmount)
                .setTotalWithDiscount(totalWithDiscount)
                .build();
    }

    private List<CheckItem> initializeCheckItems(Order order) {
        List<CheckItem> items = new ArrayList<>();

        List<OrderQuantity> orderQuantities = order.getOrderQuantities();
        DiscountCard discountCard = order.getDiscountCard();
        for (OrderQuantity orderQuantity: orderQuantities) {
            Product product = orderQuantity.getProduct();
            Double productPrice = product.getPrice();
            Integer maxAvailableQuantity = product.getQuantity();
            Integer quantity = orderQuantity.getQuantity();

            if (quantity > maxAvailableQuantity) {
                throw new QuantityOutOfBoundException("Quantity of product is more than available");
            }

            int discountPercent;
            if (product.isWholesale() && quantity >= 5) {
                discountPercent = 10;
            } else {
                if (discountCard == null) {
                    discountPercent = 0;
                } else {
                    discountPercent = discountCard.getDiscountAmount();
                }
            }

            double totalPrice = NumberUtils.floorToHundredths(productPrice * quantity);
            double totalWithDiscount = totalPrice;
            double discountAmount = 0.0;
            if (discountPercent != 0.0) {
                discountAmount = totalPrice - totalPrice * (100 - discountPercent) / 100;
                discountAmount = NumberUtils.floorToHundredths(discountAmount);

                totalWithDiscount -= discountAmount;
            }

            CheckItem item = new CheckItemBuilder()
                    .setItemPrice(productPrice)
                    .setQuantity(quantity)
                    .setDescription(product.getDescription())
                    .setDiscountAmount(discountAmount)
                    .setTotalPrice(totalPrice)
                    .setTotalWithDiscount(totalWithDiscount)
                    .setDiscountPercent(discountPercent)
                    .build();

            items.add(item);
        }
        return items;
    }

    private double calculateTotalPrice(List<CheckItem> items) {
        double totalPrice = 0.0;
        for (CheckItem item: items) {
            totalPrice += item.getTotalPrice();
        }
        return NumberUtils.floorToHundredths(totalPrice);
    }

    private double calculateDiscountAmount(List<CheckItem> items) {
        double discountAmount = 0.0;
        for (CheckItem item: items) {
            discountAmount += item.getDiscountAmount();
        }
        return NumberUtils.floorToHundredths(discountAmount);
    }
}
