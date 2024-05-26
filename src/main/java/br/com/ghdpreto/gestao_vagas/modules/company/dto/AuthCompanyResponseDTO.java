package br.com.ghdpreto.gestao_vagas.modules.company.dto;

public record AuthCompanyResponseDTO(
        String access_token,
        Long expires_in) {

}
