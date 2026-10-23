 package co.edu.usbcali.dysoweb_java.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModificarPublicacionRequest(
        @JsonProperty("autor_id") Integer autorId,
        String contenido,
        @JsonProperty("privacidad_publicacion") String privacidadPublicacion
) {
}
