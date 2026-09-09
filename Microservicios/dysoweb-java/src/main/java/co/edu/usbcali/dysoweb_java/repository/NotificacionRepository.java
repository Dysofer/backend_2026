package co.edu.usbcali.dysoweb_java.repository;

import co.edu.usbcali.dysoweb_java.domain.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    Optional<Notificacion> findById(Integer id);

    List<Notificacion> findByUsuario_IdOrderByLeidaAscCreatedAtDesc(Integer usuarioId);

}