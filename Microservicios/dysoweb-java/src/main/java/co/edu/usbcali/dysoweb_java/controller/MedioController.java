package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.dto.request.CrearMedioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerMedioResponse;
import co.edu.usbcali.dysoweb_java.service.MedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medios")
public class MedioController {

    @Autowired
    private MedioService medioService;

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
        return medioService.obtenerMedios();
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerMedioResponse> obtenerMedioPorId(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(medioService.obtenerMedioPorId(id));
    }

    @PostMapping("/crear")
    ResponseEntity<ObtenerMedioResponse> crearMedio(@RequestBody CrearMedioRequest medioRequest) throws Exception {
        ObtenerMedioResponse medioResponse = medioService.crearMedio(medioRequest);
        return new ResponseEntity<>(medioResponse, HttpStatus.CREATED);
    }
}