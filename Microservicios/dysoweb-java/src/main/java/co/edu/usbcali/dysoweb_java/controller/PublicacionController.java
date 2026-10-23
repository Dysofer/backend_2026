package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.dto.request.CrearPublicacionRequest;
import co.edu.usbcali.dysoweb_java.dto.request.ModificarPublicacionRequest;
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
    public List<ObtenerPublicacionResponse> obtenerPublicaciones() {
        return publicacionService.obtenerPublicaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerPublicacionResponse> obtenerPublicacionPorId(@PathVariable Integer id) throws Exception {
        ObtenerPublicacionResponse response = publicacionService.obtenerPublicacionPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/crear")
    public ResponseEntity<ObtenerPublicacionResponse> crearPublicacion(@RequestBody CrearPublicacionRequest publicacionRequest) throws Exception {
        ObtenerPublicacionResponse respuestaCreacion = publicacionService.crearPublicacion(publicacionRequest);
        return new ResponseEntity<>(respuestaCreacion, HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ObtenerPublicacionResponse> modificarPublicacion(
            @PathVariable Integer id,
            @RequestBody ModificarPublicacionRequest modificarPublicacionRequest) throws Exception {

        ObtenerPublicacionResponse respuestaModificacion = publicacionService.modificarPublicacion(id, modificarPublicacionRequest);
        return ResponseEntity.ok(respuestaModificacion);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Integer id) throws Exception {
        publicacionService.eliminarPublicacion(id);
        return ResponseEntity.noContent().build();
    }
}