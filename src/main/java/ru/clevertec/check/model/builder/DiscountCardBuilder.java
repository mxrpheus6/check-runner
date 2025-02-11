package main.java.ru.clevertec.check.model.builder;

import main.java.ru.clevertec.check.model.DiscountCard;

public class DiscountCardBuilder {
    private Long id;
    private String number;
    private int discountAmount;

    public DiscountCard build() {
        return new DiscountCard(id, number, discountAmount);
    }

    public DiscountCardBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public DiscountCardBuilder setNumber(String number) {
        this.number = number;
        return this;
    }

    public DiscountCardBuilder setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }
}
