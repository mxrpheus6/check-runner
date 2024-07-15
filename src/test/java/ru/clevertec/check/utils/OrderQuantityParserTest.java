package ru.clevertec.check.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.exception.ProductNotFoundException;
import ru.clevertec.check.model.OrderQuantity;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.model.builder.ProductBuilder;
import ru.clevertec.check.repository.ProductRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class OrderQuantityParserTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderQuantityParser orderQuantityParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void parseOrderQuantities_success() throws SQLException {
        String pair1 = "1-2";
        String pair2 = "5-2";

        List<String> pairs = Arrays.asList(pair1, pair2);

        Product product1 = new ProductBuilder()
                .setId(1L)
                .setDescription("Cheese")
                .setQuantity(20)
                .setPrice(2.10)
                .setWholesale(false)
                .build();

        Product product2 = new ProductBuilder()
                .setId(5L)
                .setDescription("Water")
                .setQuantity(100)
                .setPrice(0.99)
                .setWholesale(true)
                .build();

        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findProductById(5L)).thenReturn(Optional.of(product2));

        List<OrderQuantity> result = orderQuantityParser.parseOrderQuantities(pairs);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(product1, result.getFirst().getProduct());
        Assertions.assertEquals(product2, result.getLast().getProduct());
    }

    @Test
    void parseOrderQuantities_throwsProductNotFound() throws SQLException {
        String pair = "1-2";
        List<String> pairs = List.of(pair);

        when(productRepository.findProductById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class,
                () -> orderQuantityParser.parseOrderQuantities(pairs));
    }
}
