package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Medio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbcali.dysoweb_java.repository.MedioRepository;

import java.util.List;

@RestController
@RequestMapping("/medios")
public class MedioController {

    @Autowired
    private MedioRepository medioRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }


    @GetMapping("/obtener-medios")
    List<Medio> obtenerMedios() {
        return medioRepository.findAll();
    }
}