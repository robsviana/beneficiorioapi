package tech.robsondev.beneficiarioapi.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.robsondev.beneficiarioapi.dto.*;
import tech.robsondev.beneficiarioapi.entity.Beneficiario;
import tech.robsondev.beneficiarioapi.exception.BeneficiarioBusinessException;
import tech.robsondev.beneficiarioapi.repository.BeneficiarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficiarioServiceTest {
    @Mock
    private BeneficiarioRepository repository;

    @InjectMocks
    private BeneficiarioServiceImpl service;


    @Test
    void deveRetornarListaDeBeneficiarios() {
        // ARRANGE
        Beneficiario beneficiario = new Beneficiario("João", "(85) 97688-5932", "01/01/1990");
        beneficiario.setId(UUID.randomUUID());
        when(repository.findAll()).thenReturn(List.of(beneficiario));

        // ACT
        List<BeneficiarioResponseDTO> result = service.listarTodos();

        // ASSERT
        assertEquals(1, result.size());
        assertEquals("João", result.get(0).nome());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveSalvarENovoBeneficiario() {
        // ARRANGE
        BeneficiarioRequestDTO request = new BeneficiarioRequestDTO("Maria", "(27) 96798-1829", "20/05/1990");
        ArgumentCaptor<Beneficiario> captor = ArgumentCaptor.forClass(Beneficiario.class);
        when(repository.save(any(Beneficiario.class))).thenReturn(new Beneficiario("Maria", "(27) 96798-1829", "20/05/1990"));

        // ACT
        service.salvaBeneficiario(request);

        // ASSERT
        verify(repository).save(captor.capture());
        Beneficiario capturado = captor.getValue();
        assertEquals("Maria", capturado.getNome());
        assertEquals("(27) 96798-1829", capturado.getTelefone());
        assertEquals("20/05/1990", capturado.getDataNascimento());
    }

    @Test
    void deveSalvarBeneficiarioComDocumentos() {
        // ARRANGE
        var idBeneficiario = UUID.randomUUID();
        BeneficiarioRequestDTO beneficiarioDTO = new BeneficiarioRequestDTO("Paulo", "(13) 98807-7311", "10/10/1985");
        DocumentoRequestDTO documentoDTO = new DocumentoRequestDTO(idBeneficiario.toString(), "RG", "23.770.472-9");
        BeneficiarioDocumetoRequestDTO request = new BeneficiarioDocumetoRequestDTO(beneficiarioDTO, List.of(documentoDTO));

        ArgumentCaptor<Beneficiario> captor = ArgumentCaptor.forClass(Beneficiario.class);
        when(repository.save(any(Beneficiario.class))).thenReturn(new Beneficiario("Paulo", "(13) 98807-7311", "10/10/1985"));

        // ACT
        service.salvaBeneficiarioDocumento(request);

        // ASSERT
        verify(repository).save(captor.capture());
        Beneficiario capturado = captor.getValue();
        assertEquals("Paulo", capturado.getNome());
        assertEquals("(13) 98807-7311", capturado.getTelefone());
        assertEquals(1, capturado.getDocumentos().size());
        assertEquals("RG", capturado.getDocumentos().get(0).getTipoDocumento());
        assertEquals("23.770.472-9", capturado.getDocumentos().get(0).getDescricao());
    }

    @Nested
    class deveBuscarBeneficiarioPorId {
        @Test
        void deveRetornarBeneficiario() {
            // ARRANGE
            UUID id = UUID.randomUUID();
            Beneficiario beneficiario = new Beneficiario("Ana", "(11) 96767-7390", "15/03/2021");
            when(repository.findById(id)).thenReturn(Optional.of(beneficiario));

            // ACT
            BeneficiarioResponseDTO result = service.buscarBeneficiarioPorId(id);

            // ASSERT
            assertEquals("Ana", result.nome());
            assertEquals("(11) 96767-7390", result.telefone());
            verify(repository, times(1)).findById(id);
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrado() {
            // ARRANGE
            UUID id = UUID.randomUUID();
            when(repository.findById(id)).thenReturn(Optional.empty());

            /// ACT & ASSERT
            assertThrows(BeneficiarioBusinessException.class, () -> service.buscarBeneficiarioPorId(id));
            verify(repository, times(1)).findById(id);
        }
    }


    @Test
    void deveDeletarBeneficiarioExistente() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        Beneficiario beneficiario = new Beneficiario("Carlos", "(12) 99372-6249", "07/07/1975");
        when(repository.findById(id)).thenReturn(Optional.of(beneficiario));

        // ACT
        service.deletarBeneficiario(id);

        // ASSERT
        verify(repository, times(1)).delete(beneficiario);
    }

    @Nested
    class deveAtualizarBeneficiario {
        @Test
        void deveAtualizarBeneficiarioExistente() {
            // ARRANGE
            UUID id = UUID.randomUUID();
            Beneficiario beneficiario = new Beneficiario("Pedro", "(11) 97976-0801", "10/06/1982");
            BeneficiarioRequestDTO request = new BeneficiarioRequestDTO("Pedro Atualizado", "(85) 97688-5932", "11/06/1982");

            when(repository.findById(id)).thenReturn(Optional.of(beneficiario));
            when(repository.save(any(Beneficiario.class))).thenReturn(beneficiario);

            // ACT
            service.atualizarBeneficiario(id, request);

            // ASSERT
            assertEquals("Pedro Atualizado", beneficiario.getNome());
            assertEquals("(85) 97688-5932", beneficiario.getTelefone());
            verify(repository, times(1)).save(beneficiario);
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrado() {
            // ARRANGE
            UUID id = UUID.randomUUID();
            BeneficiarioRequestDTO request = new BeneficiarioRequestDTO("Nome", "Telefone", "Data");
            when(repository.findById(id)).thenReturn(Optional.empty());

            /// ACT & ASSERT
            assertThrows(BeneficiarioBusinessException.class, () -> service.atualizarBeneficiario(id, request));
            verify(repository, times(1)).findById(id);
        }
    }


}