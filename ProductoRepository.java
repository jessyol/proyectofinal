package com.ministock.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // JpaRepository nos da automáticamente métodos como:
    // findAll(), findById(), save(), deleteById(), etc.
}