package co.edu.usbcali.dysoweb_java.dto.response;

import java.time.LocalDateTime;

public record ObtenerMedioResponse(
        Integer id,
        Integer publicacionId,
        String tipo,
        String urlOriginal,
        String urlThumbnail,
        String formato,
        Integer tamanoBytes,
        Short orden,
        LocalDateTime createdAt
) {
}