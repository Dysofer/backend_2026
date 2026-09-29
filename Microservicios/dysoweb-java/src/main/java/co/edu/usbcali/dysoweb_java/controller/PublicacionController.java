package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.dto.request.CrearPublicacionRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPublicacionResponse;
import co.edu.usbcali.dysoweb_java.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    List<ObtenerPublicacionResponse> obtenerPublicaciones() {
        return publicacionService.obtenerPublicaciones();
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerPublicacionResponse> obtenerPublicacionPorId(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));

    }

    @PostMapping("/crear")
    ResponseEntity<ObtenerPublicacionResponse> crearPublicacion(@RequestBody CrearPublicacionRequest publicacionRequest) throws Exception {
        ObtenerPublicacionResponse publicacionResponse =
                publicacionService.crearPublicacion(publicacionRequest);
        return new ResponseEntity<>(publicacionResponse, HttpStatus.CREATED);
    }
}