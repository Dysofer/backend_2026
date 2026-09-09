package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Notificacion;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerNotificacionResponse;
import co.edu.usbcali.dysoweb_java.mapper.NotificacionMapper;
import co.edu.usbcali.dysoweb_java.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionRepository notificacionRepository;

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
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        return NotificacionMapper.listaNotificacionHaciaListaObtenerNotificacionResponse(notificaciones);
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerNotificacionResponse> obtenerNotificacionPorId(@PathVariable Integer id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElse(null);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        ObtenerNotificacionResponse notificacionResponse =
                NotificacionMapper.notificacionObtenerNotificacionResponse(notificacion);

        return ResponseEntity.ok(notificacionResponse);
    }
}