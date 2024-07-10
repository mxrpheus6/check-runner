package ru.clevertec.check.model;

public class CheckItem {
    private final Integer quantity;
    private final String description;
    private final Double itemPrice;
    private final Double totalPrice;
    private final Double totalWithDiscount;
    private final Integer discountPercent;
    private final Double discountAmount;

    public CheckItem(Integer quantity, String description, Double itemPrice, Double totalPrice, Double totalWithDiscount, Integer discountPercent, Double discountAmount) {
        this.quantity = quantity;
        this.description = description;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
        this.totalWithDiscount = totalWithDiscount;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Double getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }
}
