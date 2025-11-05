package com.ministock.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity // Le dice a JPA que esta clase es una tabla en la base de datos
@Table(name = "Producto")
public class Producto {

    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a la BD que genere el ID automáticamente
    private Long id;

    // --- CÓDIGO NUEVO ---
    @NotBlank(message = "El nombre no puede estar vacío") // Regla: no puede ser nulo ni estar en blanco
    private String nombre;

    private String descripcion;

    // --- CÓDIGO NUEVO ---
    @NotNull(message = "La cantidad no puede ser nula") // Regla: debe tener un valor
    @Min(value = 0, message = "La cantidad no puede ser negativa") // Regla: no puede ser menor a 0
    private int cantidad;

    // --- CÓDIGO NUEVO ---
    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private double precio;

    // --- Constructores, Getters y Setters ---

    public Producto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}