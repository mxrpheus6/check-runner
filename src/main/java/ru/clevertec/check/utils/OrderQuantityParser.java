package main.java.ru.clevertec.check.utils;

import main.java.ru.clevertec.check.exception.ExceptionHandler;
import main.java.ru.clevertec.check.exception.ProductNotFoundException;
import main.java.ru.clevertec.check.model.OrderQuantity;
import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.model.builder.OrderQuantityBuilder;
import main.java.ru.clevertec.check.repository.ProductCsvRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderQuantityParser {
    private final ExceptionHandler exceptionHandler;

    private final List<OrderQuantity> orderQuantities = new ArrayList<>();

    public OrderQuantityParser(ExceptionHandler exceptionHandler, List<String> idQuantityPairs) {
        this.exceptionHandler = exceptionHandler;
        try {
            parseOrderQuantities(idQuantityPairs);
        } catch (RuntimeException e) {
            exceptionHandler.handleException(e);
        }
    }

    public List<OrderQuantity> getOrderQuantities() {
        return orderQuantities;
    }

    public void parseOrderQuantities(List<String> pairs) {
        for (String pair: pairs) {
            String[] parts = pair.split("-");
            if (parts.length == 2) {
                Long id = Long.parseLong(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                Optional<Product> optionalProduct = ProductCsvRepository.findProductById(id);
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
    }
}
