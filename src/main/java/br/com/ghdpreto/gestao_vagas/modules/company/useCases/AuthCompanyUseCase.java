package br.com.ghdpreto.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.ghdpreto.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.ghdpreto.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.ghdpreto.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {
    // pega dados do aplication properties
    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        // validar se company existe
        var company = this.companyRepository.findByUsername(authCompanyDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuario/Senha incorretos");
                });

        // validar senha
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.password(), company.getPassword());

        if (!passwordMatches) {
            throw new UsernameNotFoundException("Usuario/Senha incorretos");
        }

        // se correta gerar token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
                // expira em 2 horas
                .withExpiresAt(expiresIn)
                // roles
                .withClaim("roles", Arrays.asList("COMPANY"))
                // add o id da company
                .withSubject(company.getId().toString())
                // assina com o algoritimo gerado
                .sign(algorithm);

        return new AuthCompanyResponseDTO(token, expiresIn.toEpochMilli());
    }
}
