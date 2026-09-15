package co.edu.usbcali.dysoweb_java.mapper;

import co.edu.usbcali.dysoweb_java.domain.Etiqueta;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerEtiquetaResponse;
import co.edu.usbcali.dysoweb_java.dto.request.CrearEtiquetaRequest;

import java.util.List;

public class EtiquetaMapper {

    public static ObtenerEtiquetaResponse etiquetaObtenerEtiquetaResponse(Etiqueta etiqueta) {

        return new ObtenerEtiquetaResponse(
                etiqueta.getId(),
                etiqueta.getNombre()
        );
    }

    public static List<ObtenerEtiquetaResponse> listaEtiquetaHaciaListaObtenerEtiquetaResponse(List<Etiqueta> etiquetas) {
        return etiquetas.stream().map(EtiquetaMapper::etiquetaObtenerEtiquetaResponse).toList();
    }

    public Etiqueta crearEtiquetaRequestAEtiqueta(CrearEtiquetaRequest etiquetaRequest) {
        return Etiqueta.builder()
                .nombre(etiquetaRequest.nombre())
                .build();
    }
}