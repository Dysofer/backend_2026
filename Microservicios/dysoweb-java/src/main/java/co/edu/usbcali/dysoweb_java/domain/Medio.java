package co.edu.usbcali.dysoweb_java.domain;

import co.edu.usbcali.dysoweb_java.domain.enums.TipoMedio;
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
@Table(name="medios")
public class Medio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "publicacion_id", nullable = false)
    private Publicacion publicacion;

    @Column(name = "tipo", nullable = false)
    private TipoMedio tipo;

    @Column(name = "url_original", nullable = false, length = 500)
    private String urlOriginal;

    @Column(name = "url_thumbnail", length = 500)
    private String urlThumbnail;

    @Column(name = "formato", length = 20)
    private String formato;

    @Column(name = "tamano_bytes")
    private Integer tamanoBytes;

    @Column(name = "orden", nullable = false)
    private Short orden;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}