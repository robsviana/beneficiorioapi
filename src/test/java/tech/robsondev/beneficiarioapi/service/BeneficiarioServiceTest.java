package tech.robsondev.beneficiarioapi.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioDocumetoRequestDTO;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioRequestDTO;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioResponseDTO;
import tech.robsondev.beneficiarioapi.dto.DocumentoRequestDTO;
import tech.robsondev.beneficiarioapi.entity.Beneficiario;
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
    private BeneficiarioService service;


    @Test
    void deveRetornarListaDeBeneficiarios() {
        // ARRANGE
        Beneficiario beneficiario = new Beneficiario("João", "999999999", "2000-01-01");
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
        BeneficiarioRequestDTO request = new BeneficiarioRequestDTO("Maria", "888888888", "1990-05-20");
        ArgumentCaptor<Beneficiario> captor = ArgumentCaptor.forClass(Beneficiario.class);
        when(repository.save(any(Beneficiario.class))).thenReturn(new Beneficiario("Maria", "888888888", "1990-05-20"));

        // ACT
        service.salvaBeneficiario(request);

        // ASSERT
        verify(repository).save(captor.capture());
        Beneficiario capturado = captor.getValue();
        assertEquals("Maria", capturado.getNome());
        assertEquals("888888888", capturado.getTelefone());
        assertEquals("1990-05-20", capturado.getDataNascimento());
    }

    @Test
    void deveSalvarBeneficiarioComDocumentos() {
        // ARRANGE
        var idBeneficiario = UUID.randomUUID();
        BeneficiarioRequestDTO beneficiarioDTO = new BeneficiarioRequestDTO("Paulo", "777777777", "1985-10-10");
        DocumentoRequestDTO documentoDTO = new DocumentoRequestDTO(idBeneficiario.toString(), "RG", "16815736926");
        BeneficiarioDocumetoRequestDTO request = new BeneficiarioDocumetoRequestDTO(beneficiarioDTO, List.of(documentoDTO));

        ArgumentCaptor<Beneficiario> captor = ArgumentCaptor.forClass(Beneficiario.class);
        when(repository.save(any(Beneficiario.class))).thenReturn(new Beneficiario("Paulo", "777777777", "1985-10-10"));

        // ACT
        service.salvaBeneficiarioDocumento(request);

        // ASSERT
        verify(repository).save(captor.capture());
        Beneficiario capturado = captor.getValue();
        assertEquals("Paulo", capturado.getNome());
        assertEquals("777777777", capturado.getTelefone());
        assertEquals(1, capturado.getDocumentos().size());
        assertEquals("RG", capturado.getDocumentos().get(0).getTipoDocumento());
        assertEquals("16815736926", capturado.getDocumentos().get(0).getDescricao());
    }

    @Nested
    class deveBuscarBeneficiarioPorId {
        @Test
        void deveRetornarBeneficiario() {
            // ARRANGE
            UUID id = UUID.randomUUID();
            Beneficiario beneficiario = new Beneficiario("Ana", "555555555", "15/03/2021");
            when(repository.findById(id)).thenReturn(Optional.of(beneficiario));

            // ACT
            BeneficiarioResponseDTO result = service.buscarBeneficiarioPorId(id);

            // ASSERT
            assertEquals("Ana", result.nome());
            assertEquals("555555555", result.telefone());
            verify(repository, times(1)).findById(id);
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrado() {
            // ARRANGE
            UUID id = UUID.randomUUID();
            when(repository.findById(id)).thenReturn(Optional.empty());

            /// ACT & ASSERT
            assertThrows(ResponseStatusException.class, () -> service.buscarBeneficiarioPorId(id));
            verify(repository, times(1)).findById(id);
        }
    }


    @Test
    void deveDeletarBeneficiarioExistente() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        Beneficiario beneficiario = new Beneficiario("Carlos", "666666666", "1975-07-07");
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
            Beneficiario beneficiario = new Beneficiario("Pedro", "444444444", "1982-06-10");
            BeneficiarioRequestDTO request = new BeneficiarioRequestDTO("Pedro Atualizado", "555555555", "1982-06-11");

            when(repository.findById(id)).thenReturn(Optional.of(beneficiario));
            when(repository.save(any(Beneficiario.class))).thenReturn(beneficiario);

            // ACT
            service.atualizarBeneficiario(id, request);

            // ASSERT
            assertEquals("Pedro Atualizado", beneficiario.getNome());
            assertEquals("555555555", beneficiario.getTelefone());
            verify(repository, times(1)).save(beneficiario);
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrado() {
            // ARRANGE
            UUID id = UUID.randomUUID();
            BeneficiarioRequestDTO request = new BeneficiarioRequestDTO("Nome", "Telefone", "Data");
            when(repository.findById(id)).thenReturn(Optional.empty());

            /// ACT & ASSERT
            assertThrows(ResponseStatusException.class, () -> service.atualizarBeneficiario(id, request));
            verify(repository, times(1)).findById(id);
        }
    }


}