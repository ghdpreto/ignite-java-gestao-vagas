package br.com.ghdpreto.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

public record ProfileCandidateResponseDTO(
                UUID id,
                String name,
                String username,
                String email,
                String description) {

}
