package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.dto.request.CrearComentarioRequest;
import co.edu.usbcali.dysoweb_java.dto.request.ModificarComentarioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerComentarioResponse;

import java.util.List;

public interface ComentarioService {

    List<ObtenerComentarioResponse> obtenerComentarios();

    ObtenerComentarioResponse obtenerComentarioPorId(Integer id) throws Exception;

    List<ObtenerComentarioResponse> obtenerComentariosPorPublicacionId(Integer publicacionId) throws Exception;

    List<ObtenerComentarioResponse> obtenerRespuestasPorComentarioPadreId(Integer comentarioPadreId) throws Exception;

    ObtenerComentarioResponse crearComentario(CrearComentarioRequest crearComentarioRequest) throws Exception;

    ObtenerComentarioResponse modificarComentario(Integer id, ModificarComentarioRequest modificarComentarioRequest) throws Exception;

    void eliminarComentario(Integer id) throws Exception;

    void eliminarComentarioFisicamente(Integer id) throws Exception;

    ObtenerComentarioResponse recuperarComentarioPorId(Integer id) throws Exception;
}