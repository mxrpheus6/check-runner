package ru.clevertec.check.model;

public class OrderQuantity {
    private Product product;
    private Integer quantity;

    public OrderQuantity(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
