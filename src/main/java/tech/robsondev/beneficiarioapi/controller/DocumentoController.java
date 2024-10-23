package tech.robsondev.beneficiarioapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.robsondev.beneficiarioapi.dto.DocumentoRequestDTO;
import tech.robsondev.beneficiarioapi.entity.Documento;
import tech.robsondev.beneficiarioapi.service.DocumentoServiceImpl;

import java.net.URI;

@RequestMapping("/v1/documento")
@RestController
@Tag(name = "Documento")
public class DocumentoController {

    @Autowired
    private DocumentoServiceImpl service;

    @Operation(summary = "Realiza o cadastro de um Documento ", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado  Documento com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar cadastro de documento"),
    })
    @PostMapping("/{idBeneficiario}")
    public ResponseEntity<Documento> cadastrarDocumento(@PathVariable String idBeneficiario, @RequestBody DocumentoRequestDTO request) {
        var documento = service.cadastrarDocumento(idBeneficiario, request);
        return ResponseEntity.created(URI.create("/v1/documento/beneficiario/" + documento.getBeneficiario().getId())).build();
    }

}
