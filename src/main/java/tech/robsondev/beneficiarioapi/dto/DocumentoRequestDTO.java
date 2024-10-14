package tech.robsondev.beneficiarioapi.dto;

import jakarta.validation.constraints.NotBlank;

public record DocumentoRequestDTO(@NotBlank String beneficiarioId, @NotBlank String tipoDocumento, @NotBlank String descricao) {
}
