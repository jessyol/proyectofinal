package com.ministock.demo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void shouldConnectToDatabase() throws SQLException {
        assertNotNull(dataSource, "DataSource should be configured");
        
        try (Connection connection = dataSource.getConnection()) {
            assertTrue(connection.isValid(1000), "Connection should be valid");
            assertFalse(connection.isClosed(), "Connection should be open");
        }
    }

    @Test
    void shouldExecuteSimpleQuery() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertEquals(1, result, "Simple query should return 1");
    }

    @Test
    void shouldPerformBasicDatabaseOperations() {
        // Create a test product
        Producto producto = new Producto();
        producto.setNombre("Test Database Product");
        producto.setPrecio(100.0);
        producto.setCantidad(5);
        producto.setDescripcion("Test Description");

        // Save to database
        Producto savedProducto = productoRepository.save(producto);
        assertNotNull(savedProducto.getId(), "Product should have an ID after saving");

        // Read from database
        assertTrue(productoRepository.findById(savedProducto.getId()).isPresent(),
                  "Should find saved product");

        // Delete from database
        productoRepository.deleteById(savedProducto.getId());
        assertFalse(productoRepository.findById(savedProducto.getId()).isPresent(),
                   "Product should be deleted");
    }
}