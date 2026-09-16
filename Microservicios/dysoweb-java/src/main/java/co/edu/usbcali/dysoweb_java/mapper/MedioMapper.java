package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Medio;
import co.edu.usbcali.dysoweb_java.domain.Publicacion;
import co.edu.usbcali.dysoweb_java.domain.enums.TipoMedio;
import co.edu.usbcali.dysoweb_java.dto.request.CrearMedioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerMedioResponse;

import java.util.List;

public class MedioMapper {

    // Hace el mapeo para obtener los medios y no traer el objeto de la base de datos
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

    public static Medio crearMedioRequestAMedio(CrearMedioRequest medioRequest, Publicacion publicacion, TipoMedio tipo, Short orden) {
        return Medio.builder()
                .publicacion(publicacion)
                .tipo(tipo)
                .urlOriginal(medioRequest.urlOriginal())
                .urlThumbnail(medioRequest.urlThumbnail())
                .formato(medioRequest.formato())
                .tamanoBytes(medioRequest.tamanoBytes())
                .orden(orden)
                .build();
    }
}