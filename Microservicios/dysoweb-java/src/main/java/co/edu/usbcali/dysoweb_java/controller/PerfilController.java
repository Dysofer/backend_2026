package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Perfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbcali.dysoweb_java.repository.PerfilRepository;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }


    @GetMapping("/obtener-perfiles")
    List<Perfil> obtenerPerfiles() {
        return perfilRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Perfil> obtenerPerfilPorId(@PathVariable Integer id){
        Perfil perfil = perfilRepository.findById(id).orElse(null);
        return ResponseEntity.ok(perfil);
    }
}

