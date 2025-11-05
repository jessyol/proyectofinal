package com.ministock.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto actualizarProducto(Long id, Producto productoDetalles) {
        // Busca el producto existente en la base de datos
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        // Actualiza todos los campos del producto existente con los detalles nuevos
        productoExistente.setNombre(productoDetalles.getNombre());
        productoExistente.setDescripcion(productoDetalles.getDescripcion());
        productoExistente.setPrecio(productoDetalles.getPrecio());
        productoExistente.setCantidad(productoDetalles.getCantidad()); // <-- CORREGIDO

        // Guarda el producto actualizado en la base de datos
        return productoRepository.save(productoExistente);
    }

    public void eliminarProducto(Long id) {
        // Verifica si el producto existe antes de intentar eliminarlo
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        productoRepository.delete(producto);
    }
}