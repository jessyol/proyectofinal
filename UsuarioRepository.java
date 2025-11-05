
package com.ministock.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Cambiamos el método para que busque por email, como lo necesita el AuthController
    Optional<Usuario> findByEmail(String email);

}