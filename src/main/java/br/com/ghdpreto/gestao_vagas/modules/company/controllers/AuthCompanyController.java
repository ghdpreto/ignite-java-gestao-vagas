package br.com.ghdpreto.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ghdpreto.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.ghdpreto.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.ghdpreto.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("company")
@Tag(name = "Empresa", description = "Informações da empresa")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("auth")
    @Operation(summary = "Auth empresa", description = "Essa função é responsável por realizar a autenticação de uma empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthCompanyResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema(implementation = ResponseEntity.class))
            })
    })
    public ResponseEntity<Object> auth(@RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        try {
            var token = this.authCompanyUseCase.execute(authCompanyDTO);

            return ResponseEntity.ok().body(token);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
