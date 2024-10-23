package tech.robsondev.beneficiarioapi.service;

import tech.robsondev.beneficiarioapi.dto.DocumentoRequestDTO;
import tech.robsondev.beneficiarioapi.dto.DocumentoResponseDTO;
import tech.robsondev.beneficiarioapi.entity.Documento;

import java.util.List;

public interface IDocumentoService {

    Documento cadastrarDocumento(String idBeneficiario, DocumentoRequestDTO request);

    List<DocumentoResponseDTO> listarDocumentoBeneficiarioPorID(String idBeneficiario);
    }
