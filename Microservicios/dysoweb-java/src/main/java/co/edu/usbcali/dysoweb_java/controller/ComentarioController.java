package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.dto.request.CrearComentarioRequest;
import co.edu.usbcali.dysoweb_java.dto.request.ModificarComentarioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerComentarioResponse;
import co.edu.usbcali.dysoweb_java.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/ping")
    public String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    public String validarEstado() {
        return "ok";
    }

    @GetMapping
    public List<ObtenerComentarioResponse> obtenerComentarios() {
        return comentarioService.obtenerComentarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerComentarioResponse> obtenerComentarioPorId(@PathVariable Integer id) throws Exception {
        ObtenerComentarioResponse response = comentarioService.obtenerComentarioPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/publicacion/{publicacionId}")
    public ResponseEntity<List<ObtenerComentarioResponse>> obtenerComentariosPorPublicacionId(@PathVariable Integer publicacionId) throws Exception {
        List<ObtenerComentarioResponse> response = comentarioService.obtenerComentariosPorPublicacionId(publicacionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/padre/{comentarioPadreId}")
    public ResponseEntity<List<ObtenerComentarioResponse>> obtenerRespuestasPorComentarioPadreId(@PathVariable Integer comentarioPadreId) throws Exception {
        List<ObtenerComentarioResponse> response = comentarioService.obtenerRespuestasPorComentarioPadreId(comentarioPadreId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/crear")
    public ResponseEntity<ObtenerComentarioResponse> crearComentario(@RequestBody CrearComentarioRequest comentarioRequest) throws Exception {
        ObtenerComentarioResponse respuestaCreacion = comentarioService.crearComentario(comentarioRequest);
        return new ResponseEntity<>(respuestaCreacion, HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ObtenerComentarioResponse> modificarComentario(
            @PathVariable Integer id,
            @RequestBody ModificarComentarioRequest modificarComentarioRequest) throws Exception {

        ObtenerComentarioResponse respuestaModificacion = comentarioService.modificarComentario(id, modificarComentarioRequest);
        return ResponseEntity.ok(respuestaModificacion);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Integer id) throws Exception {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminar/{id}/fisico")
    public ResponseEntity<Void> eliminarComentarioFisicamente(@PathVariable Integer id) throws Exception {
        comentarioService.eliminarComentarioFisicamente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<ObtenerComentarioResponse> recuperarComentarioPorId(
            @PathVariable Integer id) throws Exception {

        ObtenerComentarioResponse comentarioRecuperado = comentarioService.recuperarComentarioPorId(id);
        return ResponseEntity.ok(comentarioRecuperado);
    }
}