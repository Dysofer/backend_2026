package co.edu.usbcali.dysoweb_java.dto.response;

import java.time.LocalDateTime;

public record ObtenerComentarioResponse(
        Integer id,
        Integer publicacionId,
        Integer usuarioId,
        Integer comentarioPadreId,
        String contenido,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
}
