package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.dto.request.CrearUsuarioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerUsuarioResponse;
import co.edu.usbcali.dysoweb_java.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-usuarios")
    List<ObtenerUsuarioResponse> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerUsuarioResponse> obtenerUsuarioPorId(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @PostMapping("/crear")
    ResponseEntity<ObtenerUsuarioResponse> crearUsuario(@RequestBody CrearUsuarioRequest usuarioRequest) throws Exception {
        ObtenerUsuarioResponse usuarioResponse = usuarioService.crearUsuario(usuarioRequest);
        return new ResponseEntity<>(usuarioResponse, HttpStatus.CREATED);
    }
}