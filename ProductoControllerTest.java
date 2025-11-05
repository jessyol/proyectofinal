package com.ministock.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void shouldListProducts() throws Exception {
        // Test GET /api/products
        mockMvc.perform(get("/api/productos"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        String newProduct = """
            {
                "nombre": "Test Product",
                "precio": 99.99,
                "cantidad": 10,
                "descripcion": "Test Description"
            }
            """;

        // Test POST /api/products
        mockMvc.perform(post("/api/productos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(newProduct))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.nombre").value("Test Product"))
               .andExpect(jsonPath("$.precio").value(99.99))
               .andExpect(jsonPath("$.cantidad").value(10));
    }

    @Test
    void shouldRejectInvalidProduct() throws Exception {
        String invalidProduct = """
            {
                "nombre": "",
                "precio": 99.99,
                "stock": 10
            }
            """;

        // Test POST with invalid product
        mockMvc.perform(post("/api/productos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(invalidProduct))
               .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        // Create a product to delete
        Producto producto = new Producto();
        producto.setNombre("Delete Test");
        producto.setPrecio(10.0);
        producto.setCantidad(1);
        producto = productoRepository.save(producto);

        // Test DELETE /api/products/{id}
        mockMvc.perform(delete("/api/productos/" + producto.getId()))
               .andExpect(status().isNoContent());
    }
}