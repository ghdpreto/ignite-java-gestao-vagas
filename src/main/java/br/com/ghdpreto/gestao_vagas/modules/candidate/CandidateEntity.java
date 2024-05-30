package br.com.ghdpreto.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private UUID id;

    private String name;

    @Pattern(regexp = "\\S+", message = "O campo não deve conter espaço")
    @Schema(example = "teste_123", requiredMode = RequiredMode.REQUIRED)
    private String username;

    @Email(message = "O campo deve conter um e-mail válido")
    @Schema(example = "teste_123@example.com.br", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Length(min = 10, max = 100)
    @Schema(example = "P@ssW0rd_123", minLength = 10, maxLength = 100)
    private String password;

    private String description;

    private String curriculum;

    @CreationTimestamp
    @Schema(accessMode = AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
}
