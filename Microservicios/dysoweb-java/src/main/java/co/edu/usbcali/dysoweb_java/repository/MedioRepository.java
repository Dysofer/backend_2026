package co.edu.usbcali.dysoweb_java.repository;

import co.edu.usbcali.dysoweb_java.domain.Medio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedioRepository extends JpaRepository<Medio, Integer> {

    List<Medio> findByPublicacion_IdOrderByOrdenAsc(Integer publicacionId);

}