package ru.clevertec.check.repository;

import ru.clevertec.check.model.Product;
import ru.clevertec.check.model.builder.ProductBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProductRepository {

    private final DatabaseConnection databaseConnection;

    public ProductRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Optional<Product> findProductById(Long id) throws SQLException {
        if (id == null) {
            return Optional.empty();
        }

        Optional<Product> result = Optional.empty();
        String query = "SELECT id, description, price, quantity_in_stock, wholesale_product FROM public.product WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product product = new ProductBuilder()
                        .setId(id)
                        .setDescription(resultSet.getString("description"))
                        .setPrice(resultSet.getDouble("price"))
                        .setQuantity(resultSet.getInt("quantity_in_stock"))
                        .setWholesale(resultSet.getBoolean("wholesale_product"))
                        .build();
                result = Optional.of(product);
            }
        }
        return result;
    }

}
