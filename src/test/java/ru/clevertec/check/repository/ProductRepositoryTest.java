package ru.clevertec.check.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductRepositoryTest {

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void findProductById_productExists() throws SQLException {
        Long id = 52L;
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("description")).thenReturn("Milk");
        when(resultSet.getDouble("price")).thenReturn(1.20);
        when(resultSet.getInt("quantity_in_stock")).thenReturn(10);
        when(resultSet.getBoolean("wholesale_product")).thenReturn(true);
        Optional<Product> result = productRepository.findProductById(id);
        Assertions.assertTrue(result.isPresent());
        Product product = result.get();
        Assertions.assertEquals("Milk", product.getDescription());
        Assertions.assertEquals(1.20, product.getPrice());
        Assertions.assertEquals(10, product.getQuantity());
        Assertions.assertTrue(product.isWholesale());
    }

    @Test
    void findProductById_productDoesNotExist() throws SQLException {
        Long id = 52L;
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        Optional<Product> result = productRepository.findProductById(id);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findProductById_idIsNull() throws SQLException {
        Optional<Product> result = productRepository.findProductById(null);
        assertTrue(result.isEmpty());
        verify(preparedStatement, never()).executeQuery();
    }
}
