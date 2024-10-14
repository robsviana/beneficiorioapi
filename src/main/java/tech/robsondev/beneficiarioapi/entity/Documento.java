package tech.robsondev.beneficiarioapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_documento")
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "descricao")
    private String descricao;

    @CreationTimestamp
    private LocalDateTime dataInclusao;

    public Documento(UUID id, String tipoDocumento, String descricao, Beneficiario beneficiario) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.descricao = descricao;
        this.beneficiario = beneficiario;
    }

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "beneficiario_id")
    private Beneficiario beneficiario;

    public Documento(String tipoDocumento, String descricao, Beneficiario beneficiario) {
        this.tipoDocumento = tipoDocumento;
        this.descricao = descricao;
        this.beneficiario = beneficiario;
    }
}
