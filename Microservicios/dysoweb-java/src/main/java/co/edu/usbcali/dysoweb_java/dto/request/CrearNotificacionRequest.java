package co.edu.usbcali.dysoweb_java.dto.request;

public record CrearNotificacionRequest(
        Integer usuarioId,
        String tipo,
        Integer origenUsuarioId,
        Integer publicacionId,
        Integer comentarioId
) {
}