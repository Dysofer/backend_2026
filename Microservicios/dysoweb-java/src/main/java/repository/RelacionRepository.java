package repository;

import co.edu.usbcali.dysoweb_java.domain.Relacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelacionRepository extends JpaRepository<Relacion, Integer> {
}
