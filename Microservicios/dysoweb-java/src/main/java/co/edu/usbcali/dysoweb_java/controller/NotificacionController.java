package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Notificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbcali.dysoweb_java.repository.NotificacionRepository;

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
    List<Notificacion> obtenerNotificaciones() {
        return notificacionRepository.findAll();
    }
}
