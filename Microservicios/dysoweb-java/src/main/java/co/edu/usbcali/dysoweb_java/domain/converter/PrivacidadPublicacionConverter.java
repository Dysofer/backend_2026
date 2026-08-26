package co.edu.usbcali.dysoweb_java.domain.converter;

import co.edu.usbcali.dysoweb_java.domain.enums.PrivacidadPublicacion;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PrivacidadPublicacionConverter extends ValorDbEnumConverter<PrivacidadPublicacion> {
    public PrivacidadPublicacionConverter() {
        super(PrivacidadPublicacion.class);
    }
}
