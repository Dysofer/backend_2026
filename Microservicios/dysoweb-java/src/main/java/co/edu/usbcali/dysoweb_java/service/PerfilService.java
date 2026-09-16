package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.dto.request.CrearPerfilRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPerfilResponse;

import java.util.List;

public interface PerfilService {
    List<ObtenerPerfilResponse> obtenerPerfiles();
    ObtenerPerfilResponse obtenerPerfilPorId(Integer id) throws Exception;
    ObtenerPerfilResponse crearPerfil(CrearPerfilRequest crearPerfilRequest) throws Exception;
}