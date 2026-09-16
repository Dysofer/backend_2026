package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.domain.Comentario;
import co.edu.usbcali.dysoweb_java.domain.Notificacion;
import co.edu.usbcali.dysoweb_java.domain.Publicacion;
import co.edu.usbcali.dysoweb_java.domain.Usuario;
import co.edu.usbcali.dysoweb_java.domain.enums.TipoNotificacion;
import co.edu.usbcali.dysoweb_java.dto.request.CrearNotificacionRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerNotificacionResponse;
import co.edu.usbcali.dysoweb_java.mapper.NotificacionMapper;
import co.edu.usbcali.dysoweb_java.repository.ComentarioRepository;
import co.edu.usbcali.dysoweb_java.repository.NotificacionRepository;
import co.edu.usbcali.dysoweb_java.repository.PublicacionRepository;
import co.edu.usbcali.dysoweb_java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerNotificacionResponse> obtenerNotificaciones() {
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        return NotificacionMapper.listaNotificacionHaciaListaObtenerNotificacionResponse(notificaciones);
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerNotificacionResponse obtenerNotificacionPorId(Integer id) throws Exception {
        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }
        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero");
        }

        Optional<Notificacion> notificacionOptional = notificacionRepository.findById(id);
        if (notificacionOptional.isEmpty()) {
            throw new Exception("No se ha encontrado la Notificacion con el id: " + id);
        }

        return NotificacionMapper.notificacionObtenerNotificacionResponse(notificacionOptional.get());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerNotificacionResponse crearNotificacion(CrearNotificacionRequest crearNotificacionRequest) throws Exception {
        if (crearNotificacionRequest == null) {
            throw new Exception("El objeto a crear no puede ser nulo");
        }
        if (crearNotificacionRequest.usuarioId() == null || crearNotificacionRequest.usuarioId() <= 0) {
            throw new Exception("El id del usuario no puede ser nulo, ni inferior o igual a cero");
        }
        if (crearNotificacionRequest.tipo() == null || crearNotificacionRequest.tipo().isBlank()) {
            throw new Exception("El tipo de notificacion no puede estar vacio ni ser nulo");
        }

        // Validar que el usuario destinatario exista
        Usuario usuario = usuarioRepository.findById(crearNotificacionRequest.usuarioId())
                .orElseThrow(() -> new Exception("No se ha encontrado el Usuario con el id: " + crearNotificacionRequest.usuarioId()));

        // Validar/convertir el tipo
        TipoNotificacion tipo;
        try {
            tipo = TipoNotificacion.valueOf(crearNotificacionRequest.tipo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("El tipo de notificacion no es valido: " + crearNotificacionRequest.tipo());
        }

        // Usuario origen (opcional)
        Usuario origenUsuario = null;
        if (crearNotificacionRequest.origenUsuarioId() != null) {
            origenUsuario = usuarioRepository.findById(crearNotificacionRequest.origenUsuarioId())
                    .orElseThrow(() -> new Exception("No se ha encontrado el Usuario origen con el id: " + crearNotificacionRequest.origenUsuarioId()));
        }

        // Publicacion (opcional)
        Publicacion publicacion = null;
        if (crearNotificacionRequest.publicacionId() != null) {
            publicacion = publicacionRepository.findById(crearNotificacionRequest.publicacionId())
                    .orElseThrow(() -> new Exception("No se ha encontrado la Publicacion con el id: " + crearNotificacionRequest.publicacionId()));
        }

        // Comentario (opcional)
        Comentario comentario = null;
        if (crearNotificacionRequest.comentarioId() != null) {
            comentario = comentarioRepository.findById(crearNotificacionRequest.comentarioId())
                    .orElseThrow(() -> new Exception("No se ha encontrado el Comentario con el id: " + crearNotificacionRequest.comentarioId()));
        }

        // Mapear desde el Request hacia la Entidad de Dominio
        Notificacion notificacion = NotificacionMapper.crearNotificacionRequestANotificacion(
                crearNotificacionRequest, usuario, tipo, origenUsuario, publicacion, comentario);

        // Persistir (almacenar) informacion en la base de datos
        notificacion = notificacionRepository.save(notificacion);

        // Mapear desde Entidad de Dominio hacia el Response
        return NotificacionMapper.notificacionObtenerNotificacionResponse(notificacion);
    }
}