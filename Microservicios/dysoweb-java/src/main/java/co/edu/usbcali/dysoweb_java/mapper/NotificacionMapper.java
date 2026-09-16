package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Comentario;
import co.edu.usbcali.dysoweb_java.domain.Notificacion;
import co.edu.usbcali.dysoweb_java.domain.Publicacion;
import co.edu.usbcali.dysoweb_java.domain.Usuario;
import co.edu.usbcali.dysoweb_java.domain.enums.TipoNotificacion;
import co.edu.usbcali.dysoweb_java.dto.request.CrearNotificacionRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerNotificacionResponse;

import java.util.List;

public class NotificacionMapper {

    // Hace el mapeo para obtener las notificaciones y no traer el objeto de la base de datos
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

    public static Notificacion crearNotificacionRequestANotificacion(CrearNotificacionRequest notificacionRequest,
                                                                     Usuario usuario,
                                                                     TipoNotificacion tipo,
                                                                     Usuario origenUsuario,
                                                                     Publicacion publicacion,
                                                                     Comentario comentario) {
        return Notificacion.builder()
                .usuario(usuario)
                .tipo(tipo)
                .origenUsuario(origenUsuario)
                .publicacion(publicacion)
                .comentario(comentario)
                .leida(false)
                .build();
    }
}