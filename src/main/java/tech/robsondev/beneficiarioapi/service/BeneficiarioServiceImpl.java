package tech.robsondev.beneficiarioapi.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioDocumetoRequestDTO;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioRequestDTO;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioResponseDTO;
import tech.robsondev.beneficiarioapi.entity.Beneficiario;
import tech.robsondev.beneficiarioapi.entity.Documento;
import tech.robsondev.beneficiarioapi.exception.BeneficiarioBusinessException;
import tech.robsondev.beneficiarioapi.repository.BeneficiarioRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BeneficiarioServiceImpl implements IBeneficiarioService {

    @Autowired
    private BeneficiarioRepository repository;


    public List<BeneficiarioResponseDTO> listarTodos() {
        var beneficiarios = repository.findAll();

        return beneficiarios.stream()
                .map(as -> new BeneficiarioResponseDTO(as.getId(), as.getNome(), as.getTelefone(),
                        as.getDataNascimento(), as.getDataInclusao(), as.getDataAtualizacao())).toList();

    }

    public Beneficiario salvaBeneficiario(BeneficiarioRequestDTO request) {
        var bnf = repository.findByNome(request.nome());

        if (bnf.isPresent()) {
            throw new BeneficiarioBusinessException(request.nome() + " ja cadastrado na base de dados!");
        }
        var entity = new Beneficiario(request.nome(), request.telefone(), request.dataNascimento());

        return repository.save(entity);
    }

    public Beneficiario salvaBeneficiarioDocumento(BeneficiarioDocumetoRequestDTO request) {

        var bnf = repository.findByNome(request.beneficiario().nome());

        if (bnf.isPresent()) {
            throw new BeneficiarioBusinessException(request.beneficiario().nome() + " ja cadastrado na base de dados!");
        }

        var beneficiario = new Beneficiario(request.beneficiario().nome(), request.beneficiario().telefone(), request.beneficiario().dataNascimento());
        var documento = request.documentos().stream().map(doc -> new Documento(doc.tipoDocumento(), doc.descricao(), beneficiario)).toList();

        beneficiario.setDocumentos(documento);
        return repository.save(beneficiario);

    }


    public BeneficiarioResponseDTO buscarBeneficiarioPorId(UUID id) {
        var benenficiario = repository.findById(id).orElseThrow(() -> new BeneficiarioBusinessException("Beneficiario nao encontrado"));
        return benenficiario.toDto();
    }

    public void deletarBeneficiario(UUID id) {
        var benenficiario = repository.findById(id).orElseThrow(() -> new BeneficiarioBusinessException("Beneficiario nao encontrado para excluir"));
        repository.delete(benenficiario);

    }

    public BeneficiarioResponseDTO atualizarBeneficiario(UUID id, BeneficiarioRequestDTO request) {

        var benenficiario = repository.findById(id).orElseThrow(() -> new BeneficiarioBusinessException("Beneficiario nao encontrado para atualizar"));

        if (StringUtils.isNotBlank(request.nome())) {
            benenficiario.setNome(request.nome());
        }
        if (StringUtils.isNotBlank(request.telefone())) {
            benenficiario.setTelefone(request.telefone());
        }
        if (StringUtils.isNotBlank(request.dataNascimento())) {
            benenficiario.setDataNascimento(request.dataNascimento());
        }

        var beneficiarioAtualizado = repository.save(benenficiario);

        return beneficiarioAtualizado.toDto();
    }

}
