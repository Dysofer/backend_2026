package co.edu.usbcali.dysoweb_java.dto.request;

public record CrearPerfilRequest(
        Integer usuarioId,
        String nombre,
        String bio,
        String avatarUrl,
        String ubicacion,
        String enlace,
        String visibilidad
) {
}