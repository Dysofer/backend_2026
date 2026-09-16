package co.edu.usbcali.dysoweb_java.service;

import co.edu.usbcali.dysoweb_java.domain.Perfil;
import co.edu.usbcali.dysoweb_java.domain.Usuario;
import co.edu.usbcali.dysoweb_java.domain.enums.VisibilidadPerfil;
import co.edu.usbcali.dysoweb_java.dto.request.CrearPerfilRequest;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerPerfilResponse;
import co.edu.usbcali.dysoweb_java.mapper.PerfilMapper;
import co.edu.usbcali.dysoweb_java.repository.PerfilRepository;
import co.edu.usbcali.dysoweb_java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerPerfilResponse> obtenerPerfiles() {
        List<Perfil> perfiles = perfilRepository.findAll();
        return PerfilMapper.listaPerfilHaciaListaObtenerPerfilResponse(perfiles);
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerPerfilResponse obtenerPerfilPorId(Integer id) throws Exception {
        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }
        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero");
        }

        Optional<Perfil> perfilOptional = perfilRepository.findById(id);
        if (perfilOptional.isEmpty()) {
            throw new Exception("No se ha encontrado el Perfil con el id: " + id);
        }

        return PerfilMapper.perfilObtenerPerfilResponse(perfilOptional.get());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerPerfilResponse crearPerfil(CrearPerfilRequest crearPerfilRequest) throws Exception {
        if (crearPerfilRequest == null) {
            throw new Exception("El objeto a crear no puede ser nulo");
        }
        if (crearPerfilRequest.usuarioId() == null || crearPerfilRequest.usuarioId() <= 0) {
            throw new Exception("El id del usuario no puede ser nulo, ni inferior o igual a cero");
        }

        // Validar que el usuario exista
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(crearPerfilRequest.usuarioId());
        if (usuarioOptional.isEmpty()) {
            throw new Exception("No se ha encontrado el Usuario con el id: " + crearPerfilRequest.usuarioId());
        }

        // Validar que el usuario no tenga ya un perfil (uq_perfiles_usuario_id)
        if (perfilRepository.existsByUsuario_Id(crearPerfilRequest.usuarioId())) {
            throw new Exception("El usuario ya tiene un perfil creado");
        }

        // Validar/convertir la visibilidad (si no viene, se usa PUBLICO, igual que el default de la BD)
        VisibilidadPerfil visibilidad = VisibilidadPerfil.PUBLICO;
        if (crearPerfilRequest.visibilidad() != null && !crearPerfilRequest.visibilidad().isBlank()) {
            try {
                visibilidad = VisibilidadPerfil.valueOf(crearPerfilRequest.visibilidad().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new Exception("El valor de visibilidad no es valido: " + crearPerfilRequest.visibilidad());
            }
        }

        // Mapear desde el Request hacia la Entidad de Dominio
        Perfil perfil = PerfilMapper.crearPerfilRequestAPerfil(crearPerfilRequest, usuarioOptional.get(), visibilidad);

        // Persistir (almacenar) informacion en la base de datos
        perfil = perfilRepository.save(perfil);

        // Mapear desde Entidad de Dominio hacia el Response
        return PerfilMapper.perfilObtenerPerfilResponse(perfil);
    }
}