package co.edu.usbcali.dysoweb_java.domain;

import co.edu.usbcali.santiagoweb.domain.enums.EstadoDenuncia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name="denuncias")
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_reportante_id", nullable = false)
    private Usuario usuarioReportante;

    @ManyToOne
    @JoinColumn(name = "usuario_reportado_id")
    private Usuario usuarioReportado;

    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private Comentario comentario;

    @Column(name = "motivo", nullable = false, length = 255)
    private String motivo;

    @Column(name = "estado", nullable = false)
    private EstadoDenuncia estado;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
