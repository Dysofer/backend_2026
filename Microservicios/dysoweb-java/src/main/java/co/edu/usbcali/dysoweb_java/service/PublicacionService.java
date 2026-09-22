package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.dto.request.CrearPublicacionRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPublicacionResponse;

import java.util.List;

public interface PublicacionService {

    // Método para obtener TODAS las publicaciones
    List<ObtenerPublicacionResponse> obtenerPublicaciones();

    // Método para obtener una publicacion por su Id
    ObtenerPublicacionResponse obtenerPublicacionPorId(Integer id) throws Exception;

    // Método para crear una nueva publicación en Base de Datos
    ObtenerPublicacionResponse crearPublicacion(CrearPublicacionRequest crearPublicacionRequest) throws Exception;
}
