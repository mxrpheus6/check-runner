package ru.clevertec.check.model.builder;

import ru.clevertec.check.model.CheckItem;

public class CheckItemBuilder {
    private Integer quantity;
    private String description;
    private Double itemPrice;
    private Double totalPrice;
    private Double totalWithDiscount;
    private Integer discountPercent;
    private Double discountAmount;

    public CheckItem build() {
        //TODO: think about this
        if (quantity == null || description == null || itemPrice == null || totalPrice == null) {
            throw new IllegalStateException("Required fields are not set");
        }

        return new CheckItem(quantity, description, itemPrice, totalPrice, totalWithDiscount, discountPercent, discountAmount);
    }

    public CheckItemBuilder setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public CheckItemBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CheckItemBuilder setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public CheckItemBuilder setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public CheckItemBuilder setTotalWithDiscount(Double totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
        return this;
    }

    public CheckItemBuilder setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public CheckItemBuilder setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }
}
