package br.com.ghdpreto.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ghdpreto.gestao_vagas.exceptions.JobNotFoundException;
import br.com.ghdpreto.gestao_vagas.exceptions.UserNotFoundException;
import br.com.ghdpreto.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.ghdpreto.gestao_vagas.modules.candidate.entitie.ApplyJobEntity;
import br.com.ghdpreto.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.ghdpreto.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    // ID do Candidato
    // ID da Vaga
    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {

        // validar se o candidato existe
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // validar se a vaga existe
        this.jobRepository.findById(idJob).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        // candidato se inscrever na vaga
        ApplyJobEntity applyJobEntity = ApplyJobEntity
                .builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        return this.applyJobRepository.save(applyJobEntity);

    }
}
