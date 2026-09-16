package co.edu.usbcali.dysoweb_java.repository;

import co.edu.usbcali.dysoweb_java.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

}