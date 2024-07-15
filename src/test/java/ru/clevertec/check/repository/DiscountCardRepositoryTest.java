package ru.clevertec.check.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.model.DiscountCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DiscountCardRepositoryTest {

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private DiscountCardRepository discountCardRepository;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void testFindDiscountCardByNumber_cardExists() throws SQLException {
        String number = "1111";
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("number")).thenReturn(number);
        when(resultSet.getInt("amount")).thenReturn(10);
        Optional<DiscountCard> result = discountCardRepository.findDiscountCardByNumber(number);
        Assertions.assertTrue(result.isPresent());
        DiscountCard discountCard = result.get();
        Assertions.assertEquals(1L, discountCard.getId());
        Assertions.assertEquals(number, discountCard.getNumber());
        Assertions.assertEquals(10, discountCard.getDiscountAmount());
    }

    @Test
    void testFindDiscountCardByNumber_cardDoesNotExist() throws SQLException {
        String number = "1111";
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        Optional<DiscountCard> result = discountCardRepository.findDiscountCardByNumber(number);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testFindDiscountCardByNumber_numberIsNull() throws SQLException {
        Optional<DiscountCard> result = discountCardRepository.findDiscountCardByNumber(null);
        assertTrue(result.isEmpty());
        verify(preparedStatement, never()).executeQuery();
    }

}
