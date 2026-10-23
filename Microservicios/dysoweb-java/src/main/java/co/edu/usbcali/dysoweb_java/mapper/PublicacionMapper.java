package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Publicacion;
import co.edu.usbcali.dysoweb_java.domain.enums.PrivacidadPublicacion;
import co.edu.usbcali.dysoweb_java.dto.request.CrearPublicacionRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPublicacionResponse;

import java.util.List;

public class PublicacionMapper {

    public static ObtenerPublicacionResponse publicacionAObtenerPublicacionResponse(Publicacion publicacion) {
        // Pre-calculamos la información del autor para mayor claridad
        boolean poseeAutor = (publicacion.getAutor() != null);
        String nombreUsuarioAutor = poseeAutor ? publicacion.getAutor().getUsername() : "";
        Integer identificadorAutor = poseeAutor ? publicacion.getAutor().getId() : null;

        // Convertimos el enum a String
        String modoPrivacidad = String.valueOf(publicacion.getPrivacidad());

        // Evaluamos el campo eliminado
        boolean estaEliminado = publicacion.getDeletedAt() != null;

        return new ObtenerPublicacionResponse(
                publicacion.getId(),
                nombreUsuarioAutor,
                identificadorAutor,
                publicacion.getContenido(),
                modoPrivacidad,
                publicacion.getCreatedAt(),
                publicacion.getUpdatedAt(),
                publicacion.getDeletedAt(),
                estaEliminado // <--- 9no parámetro requerido
        );
    }

    public static List<ObtenerPublicacionResponse> listaPublicacionesHaciaListaObtenerPublicacionesResponse(List<Publicacion> publicaciones) {
        // Expresión lambda directa en lugar de referencia a método
        return publicaciones.stream()
                .map(item -> publicacionAObtenerPublicacionResponse(item))
                .toList();
    }

    public static Publicacion crearPublicacionRequestAPublicacion(CrearPublicacionRequest publicacionRq) {
        PrivacidadPublicacion enumPrivacidad = PrivacidadPublicacion.getPrivacidadPublicacion(publicacionRq.privacidadPublicacion());

        Publicacion entidadPublicacion = Publicacion.builder()
                .contenido(publicacionRq.contenido())
                .privacidad(enumPrivacidad)
                .build();

        return entidadPublicacion;
    }
}