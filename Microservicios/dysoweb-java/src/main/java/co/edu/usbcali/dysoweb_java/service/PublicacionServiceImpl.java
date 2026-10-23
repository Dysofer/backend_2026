package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.domain.Publicacion;
import co.edu.usbcali.dysoweb_java.domain.Usuario;
import co.edu.usbcali.dysoweb_java.domain.enums.PrivacidadPublicacion;
import co.edu.usbcali.dysoweb_java.dto.request.CrearPublicacionRequest;
import co.edu.usbcali.dysoweb_java.dto.request.ModificarPublicacionRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPublicacionResponse;
import co.edu.usbcali.dysoweb_java.mapper.PublicacionMapper;
import co.edu.usbcali.dysoweb_java.repository.PublicacionRepository;
import co.edu.usbcali.dysoweb_java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerPublicacionResponse> obtenerPublicaciones() {
        List<Publicacion> listaEntidades = publicacionRepository.findAll();
        return PublicacionMapper.listaPublicacionesHaciaListaObtenerPublicacionesResponse(listaEntidades);
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerPublicacionResponse obtenerPublicacionPorId(Integer id) throws Exception {
        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }
        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para buscar");
        }

        Optional<Publicacion> resultadoConsulta = publicacionRepository.findById(id);
        if (!resultadoConsulta.isPresent()) {
            throw new Exception("No existe la publicación");
        }

        return PublicacionMapper.publicacionAObtenerPublicacionResponse(resultadoConsulta.get());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerPublicacionResponse crearPublicacion(CrearPublicacionRequest crearPublicacionRequest) throws Exception {

        if (crearPublicacionRequest == null) {
            throw new Exception("El objeto a crear no puede ser nulo");
        }

        if (crearPublicacionRequest.autorId() == null) {
            throw new Exception("El autor_id de la publicación no puede ser nulo");
        }

        if (crearPublicacionRequest.autorId() <= 0) {
            throw new Exception("El autor_id de la publicación debe ser mayor a cero");
        }

        if (crearPublicacionRequest.contenido() == null || crearPublicacionRequest.contenido().isBlank()) {
            throw new Exception("El contenido de la publicación no puede estar vacío ni ser nulo");
        }

        if (crearPublicacionRequest.privacidadPublicacion() == null || crearPublicacionRequest.privacidadPublicacion().isBlank()) {
            throw new Exception("La privacidad de la publicación no puede estar vacía ni ser nula");
        }

        Usuario usuarioAutor = usuarioRepository.findById(crearPublicacionRequest.autorId())
                .orElseThrow(() -> new Exception("No se ha encontrado el autor con el id: " + crearPublicacionRequest.autorId() + "."));

        // Verificamos la existencia en la enumeración
        boolean esPrivacidadValida = Arrays.stream(PrivacidadPublicacion.values())
                .anyMatch(p -> p.toString().equalsIgnoreCase(crearPublicacionRequest.privacidadPublicacion()));

        if (!esPrivacidadValida) {
            throw new Exception("No se ha encontrado la privacidad-publicacion: " + crearPublicacionRequest.privacidadPublicacion() + ".");
        }

        Publicacion nuevaPublicacion = PublicacionMapper.crearPublicacionRequestAPublicacion(crearPublicacionRequest);
        nuevaPublicacion.setAutor(usuarioAutor);

        Publicacion publicacionGuardada = publicacionRepository.save(nuevaPublicacion);

        return PublicacionMapper.publicacionAObtenerPublicacionResponse(publicacionGuardada);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerPublicacionResponse modificarPublicacion(Integer id, ModificarPublicacionRequest modificarPublicacionRequest) throws Exception {

        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }

        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para modificar");
        }

        if (modificarPublicacionRequest == null) {
            throw new Exception("El objeto a modificar no puede ser nulo");
        }

        boolean tieneNuevoContenido = modificarPublicacionRequest.contenido() != null && !modificarPublicacionRequest.contenido().isBlank();
        boolean tieneNuevaPrivacidad = modificarPublicacionRequest.privacidadPublicacion() != null && !modificarPublicacionRequest.privacidadPublicacion().isBlank();

        if (!tieneNuevoContenido && !tieneNuevaPrivacidad) {
            throw new Exception("Debe enviar al menos el contenido o la privacidad de la publicación para modificar");
        }

        Publicacion publicacionExistente = publicacionRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe la publicación con el id: " + id + "."));

        // Control de borrado lógico
        if (publicacionExistente.getDeletedAt() != null) {
            throw new Exception("No se puede modificar una publicación eliminada");
        }

        if (tieneNuevoContenido) {
            publicacionExistente.setContenido(modificarPublicacionRequest.contenido());
        }

        if (tieneNuevaPrivacidad) {
            PrivacidadPublicacion nuevaPrivacidad = PrivacidadPublicacion.getPrivacidadPublicacion(modificarPublicacionRequest.privacidadPublicacion());

            if (nuevaPrivacidad == null) {
                throw new Exception("No se ha encontrado la privacidad-publicacion: " + modificarPublicacionRequest.privacidadPublicacion() + ".");
            }

            publicacionExistente.setPrivacidad(nuevaPrivacidad);
        }

        Publicacion publicacionActualizada = publicacionRepository.save(publicacionExistente);

        return PublicacionMapper.publicacionAObtenerPublicacionResponse(publicacionActualizada);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void eliminarPublicacion(Integer id) throws Exception {

        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }

        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para eliminar");
        }

        Publicacion publicacionAEliminar = publicacionRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe la publicación con el id: " + id + "."));

        if (publicacionAEliminar.getDeletedAt() != null) {
            throw new Exception("La publicación ya se encuentra eliminada");
        }

        // Borrado lógico intacto
        publicacionAEliminar.setDeletedAt(LocalDateTime.now());
        publicacionRepository.save(publicacionAEliminar);
    }
}