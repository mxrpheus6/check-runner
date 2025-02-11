package main.java.ru.clevertec.check.repository;

import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.model.builder.ProductBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class ProductCsvRepository {
    private final String CSV_DELIMITER = ";";
    private final String PRODUCTS_FILEPATH = "./src/main/resources/products.csv";

    public Optional<Product> findProductById(Long id) {
        Optional<Product> result = Optional.empty();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILEPATH))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_DELIMITER);
                if (data.length == 5 && data[0].equals(id.toString())) {
                    Product product = new ProductBuilder()
                            .setId(id)
                            .setDescription(data[1])
                            .setPrice(Double.parseDouble(data[2].replace(",", ".")))
                            .setQuantity(Integer.parseInt(data[3]))
                            .setWholesale(Boolean.parseBoolean(data[4]))
                            .build();
                    result = Optional.of(product);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
