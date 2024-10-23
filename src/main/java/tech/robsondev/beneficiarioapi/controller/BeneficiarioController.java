package tech.robsondev.beneficiarioapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioDocumetoRequestDTO;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioRequestDTO;
import tech.robsondev.beneficiarioapi.dto.BeneficiarioResponseDTO;
import tech.robsondev.beneficiarioapi.dto.DocumentoResponseDTO;
import tech.robsondev.beneficiarioapi.entity.Beneficiario;
import tech.robsondev.beneficiarioapi.service.BeneficiarioServiceImpl;
import tech.robsondev.beneficiarioapi.service.DocumentoServiceImpl;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/beneficiario")
@Tag(name = "Beneficiario")
@SecurityRequirement(name = "bearerAuth")
public class BeneficiarioController {


    @Autowired
    private BeneficiarioServiceImpl beneficiarioService;

    @Autowired
    private DocumentoServiceImpl documentoService;

    @Operation(summary = "Busca todos beneficiario na base de dados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "beneficiarios encontrados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar beneficiarios"),
    })
    @GetMapping
    public ResponseEntity<List<BeneficiarioResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(beneficiarioService.listarTodos());
    }


    @Operation(summary = "Realiza o cadastro de beneficiario com documentos", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado documento com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar cadastro de documentos"),
    })
    @PostMapping("/documento")
    public ResponseEntity<Void> cadastrarBeneficiarioDocumento(@RequestBody BeneficiarioDocumetoRequestDTO request) {
        var beneficiario = beneficiarioService.salvaBeneficiarioDocumento(request);
        return ResponseEntity.created(URI.create("/v1/beneficiario/" + beneficiario.getId().toString())).build();
    }

    @Operation(summary = "Busca beneficiario por Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "beneficiario encontrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar o documentos"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<BeneficiarioResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(beneficiarioService.buscarBeneficiarioPorId(UUID.fromString(id)));
    }

    @Operation(summary = "Realiza o cadastro de um beneficiario ", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado  beneficiario com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar cadastro de um beneficiario"),
    })
    @PostMapping
    public ResponseEntity<Beneficiario> cadastrarBeneficiario(@RequestBody @Valid BeneficiarioRequestDTO request) {
        var beneficiario = beneficiarioService.salvaBeneficiario(request);
        return ResponseEntity.created(URI.create("/v1/beneficiario/" + beneficiario.getId().toString())).build();
    }

    @Operation(summary = "Deleta um beneficiario ", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado beneficiario com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar beneficiario"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBeneficiario(@PathVariable String id) {
        beneficiarioService.deletarBeneficiario(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Atualiza dados de um beneficiario ", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atualizado dados do beneficiario com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualizacao do beneficiario"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarBeneficiario(@PathVariable String id, @RequestBody BeneficiarioRequestDTO request) {
        beneficiarioService.atualizarBeneficiario(UUID.fromString(id), request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca documentos do beneficiario por Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos encontrados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar o documentos"),
    })
    @GetMapping("/documentos/{id}")
    public ResponseEntity<List<DocumentoResponseDTO>> buscarDocumentosBeneficiarioPorId(@PathVariable String id) {
        return ResponseEntity.ok(documentoService.listarDocumentoBeneficiarioPorID(id));
    }

}
