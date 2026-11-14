package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.domain.Comentario;
import co.edu.usbcali.dysoweb_java.domain.Publicacion;
import co.edu.usbcali.dysoweb_java.domain.Usuario;
import co.edu.usbcali.dysoweb_java.dto.request.CrearComentarioRequest;
import co.edu.usbcali.dysoweb_java.dto.request.ModificarComentarioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerComentarioResponse;
import co.edu.usbcali.dysoweb_java.mapper.ComentarioMapper;
import co.edu.usbcali.dysoweb_java.repository.ComentarioRepository;
import co.edu.usbcali.dysoweb_java.repository.PublicacionRepository;
import co.edu.usbcali.dysoweb_java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerComentarioResponse> obtenerComentarios() {
        List<Comentario> listaEntidades = comentarioRepository.findAll();
        return ComentarioMapper.listaComentarioHaciaListaObtenerComentarioResponse(listaEntidades);
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerComentarioResponse obtenerComentarioPorId(Integer id) throws Exception {
        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }
        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para buscar");
        }

        Optional<Comentario> resultadoConsulta = comentarioRepository.findById(id);
        if (!resultadoConsulta.isPresent()) {
            throw new Exception("No existe el comentario");
        }

        return ComentarioMapper.comentarioObtenerComentarioResponse(resultadoConsulta.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerComentarioResponse> obtenerComentariosPorPublicacionId(Integer publicacionId) throws Exception {
        if (publicacionId == null) {
            throw new Exception("El id de la publicación no puede ser nulo");
        }
        if (publicacionId <= 0) {
            throw new Exception("El valor del id de la publicación no puede ser inferior o igual a cero para buscar");
        }

        if (!publicacionRepository.existsById(publicacionId)) {
            throw new Exception("No existe la publicación con el id: " + publicacionId + ".");
        }

        List<Comentario> comentarios = comentarioRepository.findByPublicacion_IdOrderByCreatedAtAsc(publicacionId);
        return ComentarioMapper.listaComentarioHaciaListaObtenerComentarioResponse(comentarios);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerComentarioResponse> obtenerRespuestasPorComentarioPadreId(Integer comentarioPadreId) throws Exception {
        if (comentarioPadreId == null) {
            throw new Exception("El id del comentario padre no puede ser nulo");
        }
        if (comentarioPadreId <= 0) {
            throw new Exception("El valor del id del comentario padre no puede ser inferior o igual a cero para buscar");
        }

        if (!comentarioRepository.existsById(comentarioPadreId)) {
            throw new Exception("No existe el comentario padre con el id: " + comentarioPadreId + ".");
        }

        List<Comentario> comentarios = comentarioRepository.findByComentarioPadre_IdOrderByCreatedAtAsc(comentarioPadreId);
        return ComentarioMapper.listaComentarioHaciaListaObtenerComentarioResponse(comentarios);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerComentarioResponse crearComentario(CrearComentarioRequest crearComentarioRequest) throws Exception {

        if (crearComentarioRequest == null) {
            throw new Exception("El objeto a crear no puede ser nulo");
        }

        if (crearComentarioRequest.publicacionId() == null) {
            throw new Exception("El publicacion_id del comentario no puede ser nulo");
        }

        if (crearComentarioRequest.publicacionId() <= 0) {
            throw new Exception("El publicacion_id del comentario debe ser mayor a cero");
        }

        if (crearComentarioRequest.usuarioId() == null) {
            throw new Exception("El usuario_id del comentario no puede ser nulo");
        }

        if (crearComentarioRequest.usuarioId() <= 0) {
            throw new Exception("El usuario_id del comentario debe ser mayor a cero");
        }

        if (crearComentarioRequest.contenido() == null || crearComentarioRequest.contenido().isBlank()) {
            throw new Exception("El contenido del comentario no puede estar vacío ni ser nulo");
        }

        if (crearComentarioRequest.contenido().length() > 1000) {
            throw new Exception("El contenido del comentario no puede superar los 1000 caracteres");
        }

        Publicacion publicacion = publicacionRepository.findById(crearComentarioRequest.publicacionId())
                .orElseThrow(() -> new Exception("No se ha encontrado la publicación con el id: " + crearComentarioRequest.publicacionId() + "."));

        if (publicacion.getDeletedAt() != null) {
            throw new Exception("No se puede comentar en una publicación eliminada");
        }

        Usuario usuario = usuarioRepository.findById(crearComentarioRequest.usuarioId())
                .orElseThrow(() -> new Exception("No se ha encontrado el usuario con el id: " + crearComentarioRequest.usuarioId() + "."));

        Comentario comentarioPadre = null;
        if (crearComentarioRequest.comentarioPadreId() != null) {
            if (crearComentarioRequest.comentarioPadreId() <= 0) {
                throw new Exception("El comentario_padre_id debe ser mayor a cero");
            }

            comentarioPadre = comentarioRepository.findById(crearComentarioRequest.comentarioPadreId())
                    .orElseThrow(() -> new Exception("No se ha encontrado el comentario padre con el id: " + crearComentarioRequest.comentarioPadreId() + "."));

            if (comentarioPadre.getDeletedAt() != null) {
                throw new Exception("No se puede responder a un comentario eliminado");
            }

            if (!comentarioPadre.getPublicacion().getId().equals(publicacion.getId())) {
                throw new Exception("El comentario padre no pertenece a la publicación indicada");
            }
        }

        Comentario nuevoComentario = ComentarioMapper.crearComentarioRequestAComentario(crearComentarioRequest);
        nuevoComentario.setPublicacion(publicacion);
        nuevoComentario.setUsuario(usuario);
        nuevoComentario.setComentarioPadre(comentarioPadre);

        Comentario comentarioGuardado = comentarioRepository.save(nuevoComentario);

        return ComentarioMapper.comentarioObtenerComentarioResponse(comentarioGuardado);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerComentarioResponse modificarComentario(Integer id, ModificarComentarioRequest modificarComentarioRequest) throws Exception {

        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }

        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para modificar");
        }

        if (modificarComentarioRequest == null) {
            throw new Exception("El objeto a modificar no puede ser nulo");
        }

        if (modificarComentarioRequest.contenido() == null || modificarComentarioRequest.contenido().isBlank()) {
            throw new Exception("El contenido del comentario no puede estar vacío ni ser nulo para modificar");
        }

        if (modificarComentarioRequest.contenido().length() > 1000) {
            throw new Exception("El contenido del comentario no puede superar los 1000 caracteres");
        }

        Comentario comentarioExistente = comentarioRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe el comentario con el id: " + id + "."));

        // Control de borrado lógico
        if (comentarioExistente.getDeletedAt() != null) {
            throw new Exception("No se puede modificar un comentario eliminado");
        }

        comentarioExistente.setContenido(modificarComentarioRequest.contenido());
        comentarioExistente.setUpdatedAt(LocalDateTime.now());

        Comentario comentarioActualizado = comentarioRepository.save(comentarioExistente);

        return ComentarioMapper.comentarioObtenerComentarioResponse(comentarioActualizado);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void eliminarComentario(Integer id) throws Exception {

        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }

        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para eliminar");
        }

        Comentario comentarioAEliminar = comentarioRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe el comentario con el id: " + id + "."));

        if (comentarioAEliminar.getDeletedAt() != null) {
            throw new Exception("El comentario ya se encuentra eliminado");
        }

        // Borrado lógico
        comentarioAEliminar.setDeletedAt(LocalDateTime.now());
        comentarioRepository.save(comentarioAEliminar);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void eliminarComentarioFisicamente(Integer id) throws Exception {

        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }

        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para eliminar");
        }

        Comentario comentarioAEliminar = comentarioRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe el comentario con el id: " + id + "."));

        comentarioRepository.delete(comentarioAEliminar);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerComentarioResponse recuperarComentarioPorId(Integer id) throws Exception {

        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }

        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para recuperar");
        }

        Comentario comentarioARecuperar = comentarioRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe el comentario con el id: " + id + "."));

        if (comentarioARecuperar.getDeletedAt() == null) {
            throw new Exception("El comentario no se encuentra eliminado");
        }

        comentarioARecuperar.setDeletedAt(null);
        comentarioARecuperar.setUpdatedAt(LocalDateTime.now());

        Comentario comentarioRecuperado = comentarioRepository.save(comentarioARecuperar);

        return ComentarioMapper.comentarioObtenerComentarioResponse(comentarioRecuperado);
    }
}