package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbcali.dysoweb_java.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping("*/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }


    @GetMapping("/obtener-usuario")
    List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }
}