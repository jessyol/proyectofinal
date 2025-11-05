package com.ministock.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos") // Ruta base para todos los métodos en este controlador
public class ProductoController {

    // Inyecta el servicio para manejar la lógica de negocio
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Endpoint para OBTENER todos los productos.
     * Responde a: GET /api/productos
     */
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.obtenerTodosLosProductos();
    }
    
    /**
     * Endpoint para OBTENER un solo producto por su ID.
     * Responde a: GET /api/productos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        // Busca el producto y devuelve 200 (OK) si lo encuentra, o 404 (Not Found) si no.
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        return producto.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para CREAR un nuevo producto.
     * Responde a: POST /api/productos
     * * --- ESTA ES LA CORRECCIÓN ---
     * Añadimos @ResponseStatus(HttpStatus.CREATED) para que devuelva 201 en lugar de 200.
     * Añadimos @Valid para activar las reglas de validación (@NotBlank, @Min) de tu entidad Producto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Devuelve el código 201 (Created)
    public Producto crearProducto(@Valid @RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    /**
     * Endpoint para ACTUALIZAR un producto existente.
     * Responde a: PUT /api/productos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto productoDetalles) {
        try {
            Producto productoActualizado = productoService.actualizarProducto(id, productoDetalles);
            return ResponseEntity.ok(productoActualizado); // Devuelve 200 (OK) con el producto actualizado
        } catch (RuntimeException e) {
            // Si el servicio lanza una excepción (porque no encontró el ID), devuelve 404
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para ELIMINAR un producto.
     * Responde a: DELETE /api/productos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build(); // Devuelve 204 (No Content)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}