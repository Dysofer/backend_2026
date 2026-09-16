package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.dto.request.CrearUsuarioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerUsuarioResponse;

import java.util.List;

public interface UsuarioService {
    List<ObtenerUsuarioResponse> obtenerUsuarios();
    ObtenerUsuarioResponse obtenerUsuarioPorId(Integer id) throws Exception;
    ObtenerUsuarioResponse crearUsuario(CrearUsuarioRequest crearUsuarioRequest) throws Exception;
}