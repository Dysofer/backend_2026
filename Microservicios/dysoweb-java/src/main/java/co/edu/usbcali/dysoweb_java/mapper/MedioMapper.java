package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Medio;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerMedioResponse;

import java.util.List;

public class MedioMapper {

    public static ObtenerMedioResponse medioObtenerMedioResponse(Medio medio) {

        if (medio == null) {
            return null;
        }

        return new ObtenerMedioResponse(
                medio.getId(),
                medio.getPublicacion().getId(),
                medio.getTipo().toString(),
                medio.getUrlOriginal(),
                medio.getUrlThumbnail(),
                medio.getFormato(),
                medio.getTamanoBytes(),
                medio.getOrden(),
                medio.getCreatedAt()
        );
    }

    public static List<ObtenerMedioResponse> listaMedioHaciaListaObtenerMedioResponse(List<Medio> medios) {
        return medios.stream().map(MedioMapper::medioObtenerMedioResponse).toList();
    }
}