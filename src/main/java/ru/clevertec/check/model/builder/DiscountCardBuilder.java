package ru.clevertec.check.model.builder;

import ru.clevertec.check.model.DiscountCard;

public class DiscountCardBuilder {
    private Long id;
    private String number;
    private Integer discountAmount;

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

    public DiscountCardBuilder setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }
}
