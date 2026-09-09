package co.edu.usbcali.dysoweb_java.dto.response;

import java.time.LocalDateTime;

public record ObtenerUsuarioResponse(
        Integer id,
        String email,
        String username,
        LocalDateTime lastlogin,
        ObtenerPerfilResponse perfil
) {
}