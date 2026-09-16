package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.dto.request.CrearNotificacionRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerNotificacionResponse;
import co.edu.usbcali.dysoweb_java.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-notificaciones")
    List<ObtenerNotificacionResponse> obtenerNotificaciones() {
        return notificacionService.obtenerNotificaciones();
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerNotificacionResponse> obtenerNotificacionPorId(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionPorId(id));
    }

    @PostMapping("/crear")
    ResponseEntity<ObtenerNotificacionResponse> crearNotificacion(@RequestBody CrearNotificacionRequest notificacionRequest) throws Exception {
        ObtenerNotificacionResponse notificacionResponse = notificacionService.crearNotificacion(notificacionRequest);
        return new ResponseEntity<>(notificacionResponse, HttpStatus.CREATED);
    }
}