
package com.ministock.demo;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {
        //  si el email ya existe, devuelve un error.
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return new ResponseEntity<>(Map.of("detail", "El email ya existe"), HttpStatus.BAD_REQUEST);
        }
        
        // Si no existe, guarda el nuevo usuario.
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Usuario usuario) {
        // Obtenemos el usuario de la BD sin necesidad de casts.
        Optional<Usuario> usuarioDb = usuarioRepository.findByEmail(usuario.getEmail());

        // Verificamos si el usuario no existe O si la contraseña es incorrecta.
        if (usuarioDb.isEmpty() || !usuarioDb.get().getPassword().equals(usuario.getPassword())) {
            return new ResponseEntity<>(Map.of("detail", "Credenciales incorrectas"), HttpStatus.UNAUTHORIZED);
        }

        // En una app real, aquí se generaría y devolvería un token JWT.
        return new ResponseEntity<>(Map.of("access_token", "dummy-token-jwt", "token_type", "bearer"), HttpStatus.OK);
    }
}