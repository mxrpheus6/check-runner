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
    private final ProductCsvRepository productCsvRepository;

    public OrderQuantityParser(ProductCsvRepository productCsvRepository) {
        this.productCsvRepository = productCsvRepository;
    }

    public List<OrderQuantity> parseOrderQuantities(List<String> pairs) {
        List<OrderQuantity> orderQuantities = new ArrayList<>();
        for (String pair: pairs) {
            String[] parts = pair.split("-");
            if (parts.length == 2) {
                Long id = Long.parseLong(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                Optional<Product> optionalProduct = productCsvRepository.findProductById(id);
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
