package co.edu.usbcali.dysoweb_java.repository;


import co.edu.usbcali.dysoweb_java.domain.Bloqueo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueoRepository  extends JpaRepository<Bloqueo, Integer> {
}