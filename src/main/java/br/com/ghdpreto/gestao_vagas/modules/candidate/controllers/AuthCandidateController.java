package br.com.ghdpreto.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ghdpreto.gestao_vagas.modules.candidate.dto.AuthCandidadateResponseDTO;
import br.com.ghdpreto.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.ghdpreto.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("auth")
    @Operation(summary = "Auth candidato", description = "Essa função é responsável por realizar a autenticação de um candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthCandidadateResponseDTO.class)) }),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
    })
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO entity) {

        try {

            var token = this.authCandidateUseCase.execute(entity);

            return ResponseEntity.ok().body(token);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}
