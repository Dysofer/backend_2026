package co.edu.usbcali.dysoweb_java.dto.request;

public record CrearUsuarioRequest(
        String email,
        String username,
        String password
) {
}
