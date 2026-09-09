package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Perfil;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPerfilResponse;
import co.edu.usbcali.dysoweb_java.mapper.PerfilMapper;
import co.edu.usbcali.dysoweb_java.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    List<ObtenerPerfilResponse> obtenerPerfiles() {
        List<Perfil> perfiles = perfilRepository.findAll();
        return PerfilMapper.listaPerfilHaciaListaObtenerPerfilResponse(perfiles);
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerPerfilResponse> obtenerPerfilPorId(@PathVariable Integer id) {
        Perfil perfil = perfilRepository.findById(id).orElse(null);

        if (perfil == null) {
            return ResponseEntity.notFound().build();
        }

        ObtenerPerfilResponse perfilResponse =
                PerfilMapper.perfilObtenerPerfilResponse(perfil);

        return ResponseEntity.ok(perfilResponse);
    }
}