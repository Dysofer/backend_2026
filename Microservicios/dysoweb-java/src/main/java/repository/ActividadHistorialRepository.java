package repository;

import co.edu.usbcali.dysoweb_java.domain.ActividadHistorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface ActividadHistorialRepository  extends JpaRepository<ActividadHistorial, Integer> {
}
