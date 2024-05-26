package br.com.ghdpreto.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ghdpreto.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.ghdpreto.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID id) {

        var candidate = this.candidateRepository.findById(id).orElseThrow(() -> {
            throw new UsernameNotFoundException("Usuário não encontrado");
        });

        return new ProfileCandidateResponseDTO(
                candidate.getId(),
                candidate.getName(),
                candidate.getUsername(),
                candidate.getEmail(),
                candidate.getDescription());
    }
}
