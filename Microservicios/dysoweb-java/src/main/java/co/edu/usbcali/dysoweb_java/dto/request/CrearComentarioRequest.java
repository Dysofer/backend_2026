package co.edu.usbcali.dysoweb_java.dto.request;

public record CrearComentarioRequest(
        Integer publicacionId,
        Integer usuarioId,
        Integer comentarioPadreId,
        String contenido
) {
}