package br.com.ghdpreto.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.ghdpreto.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.ghdpreto.gestao_vagas.modules.candidate.dto.AuthCandidadateResponseDTO;
import br.com.ghdpreto.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;

@Service
public class AuthCandidateUseCase {

    // pega dados do app properties
    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidadateResponseDTO execute(AuthCandidateRequestDTO authCandidateDTO) {
        // validar se o candidate existe
        var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuario/Senha incorretos");
                });

        // validar senha

        var passwordMatches = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new UsernameNotFoundException("Usuario/Senha incorretos");
        }

        // se correta gerar token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create().withIssuer("javagas")
                // expira em 2 horas
                .withExpiresAt(Instant.now()
                        .plus(Duration.ofHours(2)))
                // add o id do candidate
                .withSubject(candidate.getId().toString())
                // add regras de acesso
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                // assina com o algoritimo
                .sign(algorithm);

        return new AuthCandidadateResponseDTO(token, expiresIn.toEpochMilli());

    }

}
