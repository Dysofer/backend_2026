package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Etiqueta;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerEtiquetaResponse;

import java.util.List;

public class EtiquetaMapper {

    // Hace el mapeo para obtener las etiquetas y no traer el objeto de la base de datos
    public static ObtenerEtiquetaResponse etiquetaObtenerEtiquetaResponse(Etiqueta etiqueta) {

        if (etiqueta == null) {
            return null;
        }

        return new ObtenerEtiquetaResponse(
                etiqueta.getId(),
                etiqueta.getNombre()
        );
    }

    public static List<ObtenerEtiquetaResponse> listaEtiquetaHaciaListaObtenerEtiquetaResponse(List<Etiqueta> etiquetas) {
        return etiquetas.stream().map(EtiquetaMapper::etiquetaObtenerEtiquetaResponse).toList();
    }
}