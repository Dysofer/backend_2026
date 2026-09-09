package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Notificacion;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerNotificacionResponse;

import java.util.List;

public class NotificacionMapper {

    public static ObtenerNotificacionResponse notificacionObtenerNotificacionResponse(Notificacion notificacion) {

        if (notificacion == null) {
            return null;
        }

        return new ObtenerNotificacionResponse(
                notificacion.getId(),
                notificacion.getUsuario().getId(),
                notificacion.getTipo().toString(),
                notificacion.getOrigenUsuario() != null ? notificacion.getOrigenUsuario().getId() : null,
                notificacion.getPublicacion() != null ? notificacion.getPublicacion().getId() : null,
                notificacion.getComentario() != null ? notificacion.getComentario().getId() : null,
                notificacion.getLeida(),
                notificacion.getCreatedAt()
        );
    }

    public static List<ObtenerNotificacionResponse> listaNotificacionHaciaListaObtenerNotificacionResponse(List<Notificacion> notificaciones) {
        return notificaciones.stream().map(NotificacionMapper::notificacionObtenerNotificacionResponse).toList();
    }
}