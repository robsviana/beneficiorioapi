package tech.robsondev.beneficiarioapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.robsondev.beneficiarioapi.dto.DocumentoRequestDTO;
import tech.robsondev.beneficiarioapi.dto.DocumentoResponseDTO;
import tech.robsondev.beneficiarioapi.entity.Beneficiario;
import tech.robsondev.beneficiarioapi.entity.Documento;
import tech.robsondev.beneficiarioapi.repository.BeneficiarioRepository;
import tech.robsondev.beneficiarioapi.repository.DocumentoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DocumentoServiceTest {
    @InjectMocks
    private DocumentoService documentoService;

    @Mock
    private BeneficiarioRepository beneficiarioRepository;

    @Mock
    private DocumentoRepository documentoRepository;

    @Captor
    private ArgumentCaptor<Documento> documentoCaptor;

    @Test
    public void deveCadastrarDocumento() {
        // ARRANGE
        String idBeneficiario = UUID.randomUUID().toString();
        DocumentoRequestDTO request = new DocumentoRequestDTO(idBeneficiario, "RG", "23.770.472-9");

        Beneficiario beneficiario = new Beneficiario("João", "(27) 96798-1829", "22/07/1999");
        beneficiario.setId(UUID.fromString(idBeneficiario));

        Mockito.when(beneficiarioRepository.findById(UUID.fromString(idBeneficiario)))
                .thenReturn(Optional.of(beneficiario));

        // ACT
        documentoService.cadastrarDocumento(idBeneficiario, request);

        // ASSERT
        Mockito.verify(documentoRepository).save(documentoCaptor.capture());

        // Documento capturado = documentoCaptor.getValue();
        assertEquals("RG", documentoCaptor.getValue().getTipoDocumento().toString());
        assertEquals("23.770.472-9", documentoCaptor.getValue().getDescricao());
        assertEquals(beneficiario.getId(), documentoCaptor.getValue().getBeneficiario().getId());
    }

    @Test
    public void deveListarDocumentosBeneficiarioPorID() {
        // ARRANGE
        String idBeneficiario = UUID.randomUUID().toString();

        Beneficiario beneficiario = new Beneficiario("João", "(27) 96798-1829", "22/07/1999");
        beneficiario.setId(UUID.fromString(idBeneficiario));

        Documento documentoRG = new Documento(UUID.randomUUID(), "RG", "43.691.822-5", beneficiario);
        Documento documentoCPF = new Documento(UUID.randomUUID(), "CPF", "628.119.350-45", beneficiario);

        List<Documento> documentos = Arrays.asList(documentoRG, documentoCPF);

        Mockito.when(documentoRepository.findByBeneficiario_id(UUID.fromString(idBeneficiario)))
                .thenReturn(Optional.of(documentos));

        // ACT
        List<DocumentoResponseDTO> response = documentoService.listarDocumentoBeneficiarioPorID(idBeneficiario);

        // ASSERT
        assertEquals(2, response.size());
        assertEquals("RG", response.get(0).tipoDocumento());
        assertEquals("43.691.822-5", response.get(0).descricao());
        assertEquals("CPF", response.get(1).tipoDocumento());
        assertEquals("628.119.350-45", response.get(1).descricao());

        Mockito.verify(documentoRepository, Mockito.times(1)).findByBeneficiario_id(UUID.fromString(idBeneficiario));
    }

}