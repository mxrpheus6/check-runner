package main.java.ru.clevertec.check.model;

import java.util.List;

public class Order {
    private DiscountCard discountCard;
    private Double balanceDebitCard;
    private List<OrderQuantity> orderQuantities;

    public Order(DiscountCard discountCard, Double balanceDebitCard, List<OrderQuantity> orderQuantities) {
        this.discountCard = discountCard;
        this.balanceDebitCard = balanceDebitCard;
        this.orderQuantities = orderQuantities;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public Double getBalanceDebitCard() {
        return balanceDebitCard;
    }

    public List<OrderQuantity> getOrderQuantities() {
        return orderQuantities;
    }
}
