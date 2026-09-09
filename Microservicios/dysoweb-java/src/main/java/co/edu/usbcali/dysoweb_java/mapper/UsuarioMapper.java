package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Perfil;
import co.edu.usbcali.dysoweb_java.domain.Usuario;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerUsuarioResponse;

import java.util.List;

public class UsuarioMapper {

    // Hace el mapeo para obtener los usuarios por Id y no traer el objeto de la base de datos (sin perfil)
    public static ObtenerUsuarioResponse usuarioObtenerUsuarioResponse(Usuario usuario) {
        return new ObtenerUsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getLastLogin(),
                null
        );
    }

    // Versión que incluye el perfil, para usarse cuando ya se tiene el Perfil cargado aparte
    public static ObtenerUsuarioResponse usuarioConPerfilObtenerUsuarioResponse(Usuario usuario, Perfil perfil) {
        return new ObtenerUsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getLastLogin(),
                PerfilMapper.perfilObtenerPerfilResponse(perfil)
        );
    }

    public static List<ObtenerUsuarioResponse> listaUsuarioHaciaListaObtenerUsuarioResponse(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioMapper::usuarioObtenerUsuarioResponse).toList();
    }
}