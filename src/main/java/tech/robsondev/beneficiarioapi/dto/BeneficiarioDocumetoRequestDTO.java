package tech.robsondev.beneficiarioapi.dto;

import java.util.List;

public record BeneficiarioDocumetoRequestDTO(BeneficiarioRequestDTO beneficiario, List<DocumentoRequestDTO> documentos) {
}
