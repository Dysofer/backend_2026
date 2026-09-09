package co.edu.usbcali.dysoweb_java.dto.response;

import java.time.LocalDateTime;

public record ObtenerNotificacionResponse(
        Integer id,
        Integer usuarioId,
        String tipo,
        Integer origenUsuarioId,
        Integer publicacionId,
        Integer comentarioId,
        Boolean leida,
        LocalDateTime createdAt
) {
}