package main.java.ru.clevertec.check.model.builder;

import main.java.ru.clevertec.check.model.Product;

public class ProductBuilder {
    private Long id;
    private String description;
    private double price;
    private int quantity;
    private boolean isWholesale;

    public Product build() {
        return new Product(id, description, price, quantity, isWholesale);
    }

    public ProductBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBuilder setWholesale(boolean wholesale) {
        isWholesale = wholesale;
        return this;
    }
}
