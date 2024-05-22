package br.com.ghdpreto.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ghdpreto.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.ghdpreto.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("auth")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("company")
    public ResponseEntity<String> auth(@RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        try {
            var token = this.authCompanyUseCase.execute(authCompanyDTO);

            return ResponseEntity.ok().body(token);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}
