package tech.robsondev.beneficiarioapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BeneficiarioResponseDTO(UUID id, String nome, String telefone, String dataNascimento, LocalDateTime dataInclusao, LocalDateTime dataAtualizacao) {

}
