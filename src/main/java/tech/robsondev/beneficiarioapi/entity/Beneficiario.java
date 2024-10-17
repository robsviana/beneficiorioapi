package tech.robsondev.beneficiarioapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_BENEFICIARIO")
public class Beneficiario {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "data_nascimento")
    private String dataNascimento;

    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


    @OneToMany(mappedBy = "beneficiario", cascade = CascadeType.ALL)
    private List<Documento> documentos;


    public Beneficiario(String nome, String telefone, String dataNascimento) {
        this.nome = nome;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public BeneficiarioResponseDTO toDto() {
        return new BeneficiarioResponseDTO(id, nome, telefone, dataNascimento, dataInclusao, dataAtualizacao);

    }


}
