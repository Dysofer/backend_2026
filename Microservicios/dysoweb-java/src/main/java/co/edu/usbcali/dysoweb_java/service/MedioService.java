package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.dto.request.CrearMedioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerMedioResponse;

import java.util.List;

public interface MedioService {
    List<ObtenerMedioResponse> obtenerMedios();
    ObtenerMedioResponse obtenerMedioPorId(Integer id) throws Exception;
    ObtenerMedioResponse crearMedio(CrearMedioRequest crearMedioRequest) throws Exception;
}