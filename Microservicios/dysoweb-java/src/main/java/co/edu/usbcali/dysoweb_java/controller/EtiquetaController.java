package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Etiqueta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbcali.dysoweb_java.repository.EtiquetaRepository;

import java.util.List;

@RestController
@RequestMapping("/etiquetas")
public class EtiquetaController {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }


    @GetMapping("/obtener-etiquetas")
    List<Etiqueta> obtenerEtiquetas() {
        return etiquetaRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Etiqueta> obtenerEtiquetaPorId(@PathVariable Integer id){
        Etiqueta etiqueta = etiquetaRepository.findById(id).orElse(null);
        return ResponseEntity.ok(etiqueta);
    }
}