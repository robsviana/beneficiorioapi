package tech.robsondev.beneficiarioapi.service;

import tech.robsondev.beneficiarioapi.dto.BeneficiarioDocumetoRequestDTO;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioRequestDTO;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioResponseDTO;
import tech.robsondev.beneficiarioapi.entity.Beneficiario;

import java.util.List;
import java.util.UUID;

public interface IBeneficiarioService {

    List<BeneficiarioResponseDTO> listarTodos();

    Beneficiario salvaBeneficiario(BeneficiarioRequestDTO request);

    Beneficiario salvaBeneficiarioDocumento(BeneficiarioDocumetoRequestDTO request);

    BeneficiarioResponseDTO buscarBeneficiarioPorId(UUID id);

    public void deletarBeneficiario(UUID id);

    BeneficiarioResponseDTO atualizarBeneficiario(UUID id, BeneficiarioRequestDTO request);


}
