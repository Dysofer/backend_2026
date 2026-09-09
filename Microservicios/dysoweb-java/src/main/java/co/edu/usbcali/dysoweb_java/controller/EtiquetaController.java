package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Etiqueta;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerEtiquetaResponse;
import co.edu.usbcali.dysoweb_java.mapper.EtiquetaMapper;
import co.edu.usbcali.dysoweb_java.repository.EtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    List<ObtenerEtiquetaResponse> obtenerEtiquetas() {
        List<Etiqueta> etiquetas = etiquetaRepository.findAll();
        return EtiquetaMapper.listaEtiquetaHaciaListaObtenerEtiquetaResponse(etiquetas);
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerEtiquetaResponse> obtenerEtiquetaPorId(@PathVariable Integer id) {
        Etiqueta etiqueta = etiquetaRepository.findById(id).orElse(null);

        if (etiqueta == null) {
            return ResponseEntity.notFound().build();
        }

        ObtenerEtiquetaResponse etiquetaResponse =
                EtiquetaMapper.etiquetaObtenerEtiquetaResponse(etiqueta);

        return ResponseEntity.ok(etiquetaResponse);
    }
}