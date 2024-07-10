package ru.clevertec.check.utils;

import ru.clevertec.check.exception.ProductNotFoundException;
import ru.clevertec.check.model.OrderQuantity;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.model.builder.OrderQuantityBuilder;
import ru.clevertec.check.repository.ProductRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderQuantityParser {
    private final ProductRepository productRepository;

    public OrderQuantityParser(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<OrderQuantity> parseOrderQuantities(List<String> pairs) throws SQLException {
        List<OrderQuantity> orderQuantities = new ArrayList<>();
        for (String pair: pairs) {
            String[] parts = pair.split("-");
            if (parts.length == 2) {
                Long id = Long.parseLong(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                Optional<Product> optionalProduct = productRepository.findProductById(id);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();

                    OrderQuantity orderQuantity = new OrderQuantityBuilder()
                            .setProduct(product)
                            .setQuantity(quantity)
                            .build();
                    orderQuantities.add(orderQuantity);
                } else {
                    throw new ProductNotFoundException("There is no product with id=" + id);
                }
            }
        }
        return orderQuantities;
    }
}
