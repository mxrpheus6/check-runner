package ru.clevertec.check.repository;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.builder.DiscountCardBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DiscountCardRepository {
    private final DatabaseConnection databaseConnection;

    public DiscountCardRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Optional<DiscountCard> findDiscountCardByNumber(String number) throws SQLException {
        if (number == null) {
            return Optional.empty();
        }

        Optional<DiscountCard> result = Optional.empty();
        String query = "SELECT id, number, amount FROM public.discount_card WHERE number = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(number));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                DiscountCard discountCard = new DiscountCardBuilder()
                        .setId(resultSet.getLong("id"))
                        .setNumber(resultSet.getString("number"))
                        .setDiscountAmount(resultSet.getInt("amount"))
                        .build();
                result = Optional.of(discountCard);
            }
        }
        return result;
    }
}
