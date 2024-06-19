package br.com.ghdpreto.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

    public static String objectToJson(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // token fake para testes
    public static String generateToken(UUID idCompany) {

        // se correta gerar token
        Algorithm algorithm = Algorithm.HMAC256("JAVAGAS_@123#");

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
                // expira em 2 horas
                .withExpiresAt(expiresIn)
                // roles
                .withClaim("roles", Arrays.asList("COMPANY"))
                // add o id da company
                .withSubject(idCompany.toString())
                // assina com o algoritimo gerado
                .sign(algorithm);

        return token;
    }

}
