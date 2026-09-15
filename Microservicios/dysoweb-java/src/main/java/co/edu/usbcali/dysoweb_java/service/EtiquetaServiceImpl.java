package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.domain.Etiqueta;
import co.edu.usbcali.dysoweb_java.dto.request.CrearEtiquetaRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerEtiquetaResponse;
import co.edu.usbcali.dysoweb_java.mapper.EtiquetaMapper;
import co.edu.usbcali.dysoweb_java.repository.EtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtiquetaServiceImpl implements EtiquetaService {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Override
    public List<ObtenerEtiquetaResponse> obtenerEtiquetas() {
        List<Etiqueta> todaslasEtiquetas = etiquetaRepository.findAll();
        List<ObtenerEtiquetaResponse> etiquetasResponses =
                EtiquetaMapper.listaEtiquetaHaciaListaObtenerEtiquetaResponse(todaslasEtiquetas);
        return etiquetasResponses;

        //return EtiquetaMapper.listaEtiquetaHaciaListaObtenerEtiquetaResponse(todaslasEtiquetas);
    }

    @Override
    public ObtenerEtiquetaResponse obtenerEtiquetaPorId(Integer id) throws Exception {
        //Validar que id no sea nulo, ni vacio ni sea valor inferior a cero

        if(id == null){
            throw new Exception("El id no puede ser nulo");
        }
        if (id <=0){
            throw new Exception("El valor del id no puede ser inferior o igual a cero");
        }
        Optional<Etiqueta> etiquetaOptional = etiquetaRepository.findById(id);
        if (etiquetaOptional.isEmpty()){
            throw new Exception("No se ha encontrado la Etiqueta con el id: "+id);
        }
        ObtenerEtiquetaResponse etiquetaResponse =
                EtiquetaMapper.etiquetaObtenerEtiquetaResponse(etiquetaOptional.get());
        return etiquetaResponse;
    }

    @Override
    public ObtenerEtiquetaResponse crearEtiqueta(CrearEtiquetaRequest crearEtiqueta) throws Exception {
        return null;
    }
}
