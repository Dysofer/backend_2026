package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.domain.Perfil;
import co.edu.usbcali.dysoweb_java.domain.Usuario;
import co.edu.usbcali.dysoweb_java.dto.request.CrearUsuarioRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerUsuarioResponse;
import co.edu.usbcali.dysoweb_java.mapper.UsuarioMapper;
import co.edu.usbcali.dysoweb_java.repository.PerfilRepository;
import co.edu.usbcali.dysoweb_java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerUsuarioResponse> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return UsuarioMapper.listaUsuarioHaciaListaObtenerUsuarioResponse(usuarios);
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerUsuarioResponse obtenerUsuarioPorId(Integer id) throws Exception {
        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }
        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("No se ha encontrado el Usuario con el id: " + id);
        }

        Perfil perfil = perfilRepository.findByUsuario_Id(id).orElse(null);

        return UsuarioMapper.usuarioConPerfilObtenerUsuarioResponse(usuarioOptional.get(), perfil);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerUsuarioResponse crearUsuario(CrearUsuarioRequest crearUsuarioRequest) throws Exception {
        if (crearUsuarioRequest == null) {
            throw new Exception("El objeto a crear no puede ser nulo");
        }
        if (crearUsuarioRequest.email() == null || crearUsuarioRequest.email().isBlank()) {
            throw new Exception("El email no puede estar vacio ni ser nulo");
        }
        if (crearUsuarioRequest.username() == null || crearUsuarioRequest.username().isBlank()) {
            throw new Exception("El username no puede estar vacio ni ser nulo");
        }
        if (crearUsuarioRequest.password() == null || crearUsuarioRequest.password().isBlank()) {
            throw new Exception("La contraseña no puede estar vacia ni ser nula");
        }

        // Validar con Repository que no exista un usuario con ese email o username
        if (usuarioRepository.existsByEmail(crearUsuarioRequest.email())) {
            throw new Exception("El email ya se encuentra registrado");
        }
        if (usuarioRepository.existsByUsername(crearUsuarioRequest.username())) {
            throw new Exception("El username ya se encuentra registrado");
        }

        // Mapear desde el Request hacia la Entidad de Dominio
        Usuario usuario = UsuarioMapper.crearUsuarioRequestAUsuario(crearUsuarioRequest);

        // Persistir (almacenar) informacion en la base de datos
        usuario = usuarioRepository.save(usuario);

        // Mapear desde Entidad de Dominio hacia el Response (usuario recien creado, aun sin perfil)
        return UsuarioMapper.usuarioObtenerUsuarioResponse(usuario);
    }
}