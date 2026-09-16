package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.dto.request.CrearNotificacionRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerNotificacionResponse;

import java.util.List;

public interface NotificacionService {
    List<ObtenerNotificacionResponse> obtenerNotificaciones();
    ObtenerNotificacionResponse obtenerNotificacionPorId(Integer id) throws Exception;
    ObtenerNotificacionResponse crearNotificacion(CrearNotificacionRequest crearNotificacionRequest) throws Exception;
}