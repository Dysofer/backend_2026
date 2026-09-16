package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.dto.request.CrearPerfilRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPerfilResponse;
import co.edu.usbcali.dysoweb_java.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

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
        return perfilService.obtenerPerfiles();
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerPerfilResponse> obtenerPerfilPorId(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(perfilService.obtenerPerfilPorId(id));
    }

    @PostMapping("/crear")
    ResponseEntity<ObtenerPerfilResponse> crearPerfil(@RequestBody CrearPerfilRequest perfilRequest) throws Exception {
        ObtenerPerfilResponse perfilResponse = perfilService.crearPerfil(perfilRequest);
        return new ResponseEntity<>(perfilResponse, HttpStatus.CREATED);
    }
}