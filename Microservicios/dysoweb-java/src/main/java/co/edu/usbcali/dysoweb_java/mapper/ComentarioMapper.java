package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Comentario;
import co.edu.usbcali.dysoweb_java.dto.request.CrearComentarioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerComentarioResponse;

import java.util.List;

public class ComentarioMapper {

    public static ObtenerComentarioResponse comentarioObtenerComentarioResponse(Comentario comentario) {
        Integer publicacionId = (comentario.getPublicacion() != null) ? comentario.getPublicacion().getId() : null;
        Integer usuarioId = (comentario.getUsuario() != null) ? comentario.getUsuario().getId() : null;
        Integer comentarioPadreId = (comentario.getComentarioPadre() != null) ? comentario.getComentarioPadre().getId() : null;

        return new ObtenerComentarioResponse(
                comentario.getId(),
                publicacionId,
                usuarioId,
                comentarioPadreId,
                comentario.getContenido(),
                comentario.getCreatedAt(),
                comentario.getUpdatedAt(),
                comentario.getDeletedAt()
        );
    }

    public static List<ObtenerComentarioResponse> listaComentarioHaciaListaObtenerComentarioResponse(List<Comentario> comentarios) {
        return comentarios.stream()
                .map(ComentarioMapper::comentarioObtenerComentarioResponse)
                .toList();
    }

    public static Comentario crearComentarioRequestAComentario(CrearComentarioRequest comentarioRq) {
        return Comentario.builder()
                .contenido(comentarioRq.contenido())
                .build();
    }
}