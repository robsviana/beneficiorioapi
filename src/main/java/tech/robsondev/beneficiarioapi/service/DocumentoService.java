package tech.robsondev.beneficiarioapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.robsondev.beneficiarioapi.dto.DocumentoRequestDTO;
import tech.robsondev.beneficiarioapi.dto.DocumentoResponseDTO;
import tech.robsondev.beneficiarioapi.entity.Documento;
import tech.robsondev.beneficiarioapi.exception.BeneficiarioBusinessException;
import tech.robsondev.beneficiarioapi.exception.DocumentoBusinessException;
import tech.robsondev.beneficiarioapi.repository.BeneficiarioRepository;
import tech.robsondev.beneficiarioapi.repository.DocumentoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    public Documento cadastrarDocumento(String idBeneficiario, DocumentoRequestDTO request) {

        var beneficiario = beneficiarioRepository.findById(UUID.fromString(idBeneficiario)).orElseThrow(() -> new BeneficiarioBusinessException("Beneficiario nao encontrado"));
        var documento = new Documento(beneficiario.getId(), request.tipoDocumento(), request.descricao(), beneficiario);
        return documentoRepository.save(documento);
    }

    public List<DocumentoResponseDTO> listarDocumentoBeneficiarioPorID(String idBeneficiario) {

        var documentos = documentoRepository.findByBeneficiario_id(UUID.fromString(idBeneficiario)).get();

        if (documentos.isEmpty()) {
            throw new DocumentoBusinessException("Nao foram encontrados documentos do beneficiario com ID "+idBeneficiario);
        }
        return documentos.stream().map(doc -> new DocumentoResponseDTO(doc.getTipoDocumento(), doc.getDescricao())).toList();

    }

}