package br.com.ghdpreto.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private UUID id;

    @Schema(requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(requiredMode = RequiredMode.REQUIRED)
    private String benefits;

    @NotBlank(message = "Campo obrigtório")
    @Schema(requiredMode = RequiredMode.REQUIRED)
    private String level;

    // relacionamento de tabelas
    // varios jobs para 1 company
    @ManyToOne()
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    @Schema(hidden = true)
    private CompanyEntity companyEntity;

    @Column(name = "company_id", nullable = false)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private UUID companyId;

    @CreationTimestamp
    @Schema(accessMode = AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

}
