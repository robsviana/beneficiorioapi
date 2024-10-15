package tech.robsondev.beneficiarioapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.robsondev.beneficiarioapi.dto.AuthetinticationDTO;
import tech.robsondev.beneficiarioapi.dto.RegisterDTO;
import tech.robsondev.beneficiarioapi.security.AuthorizationService;

@RestController
@RequestMapping("v1/auth")
@Tag(name = "Authentication Authorization API")
@SecurityRequirement(name = "bearerAuth")
public class AuthAuthorizeApiController {

    @Autowired
    AuthorizationService authorizationService;

    @Operation(summary = "Realiza o login na aplicação para gerar tokens jwt de acessos", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar login"),
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthetinticationDTO authetinticationDto) {
        return authorizationService.login(authetinticationDto);

    }

    @Operation(summary = "Registra um usuário para acessar a aplicação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registrado usuario com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao registrar usuario"),
    })
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDto) {
        return authorizationService.register(registerDto);
    }


}
