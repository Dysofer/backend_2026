package co.edu.usbcali.dysoweb_java.controller;

import co.edu.usbcali.dysoweb_java.domain.Perfil;
import co.edu.usbcali.dysoweb_java.domain.Usuario;
import co.edu.usbcali.dysoweb_java.dto.response.ObtenerUsuarioResponse;
import co.edu.usbcali.dysoweb_java.mapper.UsuarioMapper;
import co.edu.usbcali.dysoweb_java.repository.PerfilRepository;
import co.edu.usbcali.dysoweb_java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-usuarios")
    List<ObtenerUsuarioResponse> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return UsuarioMapper.listaUsuarioHaciaListaObtenerUsuarioResponse(usuarios);
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerUsuarioResponse> obtenerUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        Perfil perfil = perfilRepository.findByUsuario_Id(id).orElse(null);

        ObtenerUsuarioResponse usuarioResponse =
                UsuarioMapper.usuarioConPerfilObtenerUsuarioResponse(usuario, perfil);

        return ResponseEntity.ok(usuarioResponse);
    }
}