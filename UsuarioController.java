package com.ministock.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    // Simulación de la base de datos
    private List<Usuario> usuariosDb = List.of(
        // Asegúrate de que los constructores coincidan con tu clase Usuario
        new Usuario("Admin", "admin@ministock.com", "admin", "admin"),
        new Usuario("Juan", "juan@ministock.com", "juan123", "user")
    );

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuariosDb;
    }
}