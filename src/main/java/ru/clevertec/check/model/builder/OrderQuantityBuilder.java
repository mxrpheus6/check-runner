package ru.clevertec.check.model.builder;

import ru.clevertec.check.model.OrderQuantity;
import ru.clevertec.check.model.Product;

public class OrderQuantityBuilder {
    private Product product;
    private int quantity;

    public OrderQuantity build() {
        return new OrderQuantity(product, quantity);
    }

    public OrderQuantityBuilder setProduct(Product product) {
        this.product = product;
        return this;
    }

    public OrderQuantityBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
