package ru.clevertec.check.model.builder;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Order;
import ru.clevertec.check.model.OrderQuantity;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private DiscountCard discountCard;
    private Double balanceDebitCard;
    private List<OrderQuantity> orderQuantities = new ArrayList<>();

    public Order build() {
        return new Order(discountCard, balanceDebitCard, orderQuantities);
    }

    public OrderBuilder setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
        return this;
    }

    public OrderBuilder setBalanceDebitCard(Double balanceDebitCard) {
        this.balanceDebitCard = balanceDebitCard;
        return this;
    }

    public OrderBuilder setOrderQuantities(List<OrderQuantity> orderQuantities) {
        this.orderQuantities = orderQuantities;
        return this;
    }
}
