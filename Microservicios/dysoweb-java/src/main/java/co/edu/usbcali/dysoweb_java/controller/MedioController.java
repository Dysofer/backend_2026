package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Medio;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerMedioResponse;
import co.edu.usbcali.dysoweb_java.mapper.MedioMapper;
import co.edu.usbcali.dysoweb_java.repository.MedioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    List<ObtenerMedioResponse> obtenerMedios() {
        List<Medio> medios = medioRepository.findAll();
        return MedioMapper.listaMedioHaciaListaObtenerMedioResponse(medios);
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerMedioResponse> obtenerMedioPorId(@PathVariable Integer id) {
        Medio medio = medioRepository.findById(id).orElse(null);

        if (medio == null) {
            return ResponseEntity.notFound().build();
        }

        ObtenerMedioResponse medioResponse =
                MedioMapper.medioObtenerMedioResponse(medio);

        return ResponseEntity.ok(medioResponse);
    }

    @GetMapping("/publicacion/{publicacionId}")
    List<ObtenerMedioResponse> obtenerMediosPorPublicacionId(@PathVariable Integer publicacionId) {
        List<Medio> medios = medioRepository.findByPublicacion_IdOrderByOrdenAsc(publicacionId);
        return MedioMapper.listaMedioHaciaListaObtenerMedioResponse(medios);
    }
}