package co.edu.usbcali.dysoweb_java.dto.response;

import java.time.LocalDateTime;

public record ObtenerPerfilResponse(
        Integer id,
        Integer usuarioId,
        String nombre,
        String bio,
        String avatarUrl,
        String ubicacion,
        String enlace,
        String visibilidad,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}