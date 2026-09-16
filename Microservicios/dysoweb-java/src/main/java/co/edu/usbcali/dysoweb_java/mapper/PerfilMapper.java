package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Perfil;
import co.edu.usbcali.dysoweb_java.domain.Usuario;
import co.edu.usbcali.dysoweb_java.domain.enums.VisibilidadPerfil;
import co.edu.usbcali.dysoweb_java.dto.request.CrearPerfilRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPerfilResponse;

import java.util.List;

public class PerfilMapper {

    // Hace el mapeo para obtener el perfil por Id y no traer el objeto de la base de datos
    public static ObtenerPerfilResponse perfilObtenerPerfilResponse(Perfil perfil) {

        if (perfil == null) {
            return null;
        }

        return new ObtenerPerfilResponse(
                perfil.getId(),
                perfil.getUsuario().getId(),
                perfil.getNombre(),
                perfil.getBio(),
                perfil.getAvatarUrl(),
                perfil.getUbicacion(),
                perfil.getEnlace(),
                perfil.getVisibilidad().toString(),
                perfil.getCreatedAt(),
                perfil.getUpdatedAt()
        );
    }

    public static List<ObtenerPerfilResponse> listaPerfilHaciaListaObtenerPerfilResponse(List<Perfil> perfiles) {
        return perfiles.stream().map(PerfilMapper::perfilObtenerPerfilResponse).toList();
    }

    public static Perfil crearPerfilRequestAPerfil(CrearPerfilRequest perfilRequest, Usuario usuario, VisibilidadPerfil visibilidad) {
        return Perfil.builder()
                .usuario(usuario)
                .nombre(perfilRequest.nombre())
                .bio(perfilRequest.bio())
                .avatarUrl(perfilRequest.avatarUrl())
                .ubicacion(perfilRequest.ubicacion())
                .enlace(perfilRequest.enlace())
                .visibilidad(visibilidad)
                .build();
    }
}