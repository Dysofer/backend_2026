package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.domain.Medio;
import co.edu.usbcali.dysoweb_java.domain.Publicacion;
import co.edu.usbcali.dysoweb_java.domain.enums.TipoMedio;
import co.edu.usbcali.dysoweb_java.dto.request.CrearMedioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerMedioResponse;
import co.edu.usbcali.dysoweb_java.mapper.MedioMapper;
import co.edu.usbcali.dysoweb_java.repository.MedioRepository;
import co.edu.usbcali.dysoweb_java.repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedioServiceImpl implements MedioService {

    @Autowired
    private MedioRepository medioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerMedioResponse> obtenerMedios() {
        List<Medio> medios = medioRepository.findAll();
        return MedioMapper.listaMedioHaciaListaObtenerMedioResponse(medios);
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerMedioResponse obtenerMedioPorId(Integer id) throws Exception {
        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }
        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero");
        }

        Optional<Medio> medioOptional = medioRepository.findById(id);
        if (medioOptional.isEmpty()) {
            throw new Exception("No se ha encontrado el Medio con el id: " + id);
        }

        return MedioMapper.medioObtenerMedioResponse(medioOptional.get());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerMedioResponse crearMedio(CrearMedioRequest crearMedioRequest) throws Exception {
        if (crearMedioRequest == null) {
            throw new Exception("El objeto a crear no puede ser nulo");
        }
        if (crearMedioRequest.publicacionId() == null || crearMedioRequest.publicacionId() <= 0) {
            throw new Exception("El id de la publicacion no puede ser nulo, ni inferior o igual a cero");
        }
        if (crearMedioRequest.urlOriginal() == null || crearMedioRequest.urlOriginal().isBlank()) {
            throw new Exception("La url original no puede estar vacia ni ser nula");
        }

        // Validar que la publicacion exista
        Publicacion publicacion = publicacionRepository.findById(crearMedioRequest.publicacionId())
                .orElseThrow(() -> new Exception("No se ha encontrado la Publicacion con el id: " + crearMedioRequest.publicacionId()));

        // Validar/convertir el tipo (si no viene, se usa IMAGEN, igual que el default de la BD)
        TipoMedio tipo = TipoMedio.IMAGEN;
        if (crearMedioRequest.tipo() != null && !crearMedioRequest.tipo().isBlank()) {
            try {
                tipo = TipoMedio.valueOf(crearMedioRequest.tipo().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new Exception("El tipo de medio no es valido: " + crearMedioRequest.tipo());
            }
        }

        // El orden por defecto es 0 si no viene en el request
        Short orden = crearMedioRequest.orden() != null ? crearMedioRequest.orden() : 0;

        // Mapear desde el Request hacia la Entidad de Dominio
        Medio medio = MedioMapper.crearMedioRequestAMedio(crearMedioRequest, publicacion, tipo, orden);

        // Persistir (almacenar) informacion en la base de datos
        medio = medioRepository.save(medio);

        // Mapear desde Entidad de Dominio hacia el Response
        return MedioMapper.medioObtenerMedioResponse(medio);
    }
}