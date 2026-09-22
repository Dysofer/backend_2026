package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Publicacion;
import co.edu.usbcali.dysoweb_java.domain.enums.PrivacidadPublicacion;
import co.edu.usbcali.dysoweb_java.dto.request.CrearPublicacionRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPublicacionResponse;

import java.util.List;

public class PublicacionMapper {
    public static ObtenerPublicacionResponse publicacionAObtenerPublicacionResponse (Publicacion publicacion){
        ObtenerPublicacionResponse publicacionResponse = new ObtenerPublicacionResponse(
                publicacion.getId(),
                // if ternario para validar que venga la informacion del autor
                publicacion.getAutor() != null ? publicacion.getAutor().getUsername() : "",
                publicacion.getAutor() != null ? publicacion.getAutor().getId() : null,
                publicacion.getContenido(),
                publicacion.getPrivacidad().toString(),
                publicacion.getCreatedAt(),
                publicacion.getUpdatedAt()
        );
        return publicacionResponse;
    }

    public static List<ObtenerPublicacionResponse> listaPublicacionesHaciaListaObtenerPublicacionesResponse(List<Publicacion> publicaciones){
        return publicaciones.stream().map(PublicacionMapper::publicacionAObtenerPublicacionResponse).toList();
    }

    public Publicacion crearPublicacionRequestAPublicacion(CrearPublicacionRequest publicacionRq){
        Publicacion publicacion = Publicacion.builder()
                .contenido(publicacionRq.contenido())
                .privacidad(PrivacidadPublicacion.valueOf(publicacionRq.privacidadPublicacion()))
                .build();
        return publicacion;
    }

}
