package co.edu.usbcali.dysoweb_java.repository;

import co.edu.usbcali.dysoweb_java.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    List<Comentario> findByPublicacion_IdOrderByCreatedAtAsc(Integer publicacionId);

    List<Comentario> findByComentarioPadre_IdOrderByCreatedAtAsc(Integer comentarioPadreId);
}