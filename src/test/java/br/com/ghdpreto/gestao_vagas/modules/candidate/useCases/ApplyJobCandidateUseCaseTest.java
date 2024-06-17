package br.com.ghdpreto.gestao_vagas.modules.candidate.useCases;

import org.hibernate.engine.jdbc.env.spi.IdentifierCaseStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import javax.swing.text.html.Option;

import br.com.ghdpreto.gestao_vagas.exceptions.JobNotFoundException;
import br.com.ghdpreto.gestao_vagas.exceptions.UserNotFoundException;
import br.com.ghdpreto.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.ghdpreto.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.ghdpreto.gestao_vagas.modules.candidate.entitie.ApplyJobEntity;
import br.com.ghdpreto.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.ghdpreto.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ghdpreto.gestao_vagas.modules.company.repositories.JobRepository;

//mockando dados
@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    // mockando usecase
    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    // mock para classe que sao dependencias do use case
    @Mock
    private CandidateRepository candidateRepository;

    // mock para classe que sao dependencias do use case
    @Mock
    private ApplyJobRepository applyJobRepository;

    // mock para classe que sao dependencias do use case
    @Mock
    private JobRepository jobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidade not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found() {

        try {

            this.applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be abe to apply job if job not foud")
    public void should_not_be_able_to_apply_job_if_job_not_foud() {
        var idCandidate = UUID.randomUUID();

        CandidateEntity candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        // criando um retorno in memory
        // quando chamar o findById com o id criado, retorna o candidato
        when(candidateRepository.findById(idCandidate))
                .thenReturn(Optional.of(candidate));

        try {
            this.applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }

    }

    @Test
    @DisplayName("Shoulb be able to create a new apply job")
    public void shoulb_be_able_to_create_a_new_apply_job() {
        UUID idCandidate = UUID.randomUUID();
        UUID idJob = UUID.randomUUID();

        var applyJob = ApplyJobEntity
                .builder()
                .jobId(idJob)
                .candidateId(idCandidate)
                .build();

        var applyJobCreated = ApplyJobEntity
                .builder()
                .id(UUID.randomUUID())
                .build();

        when(candidateRepository.findById(idCandidate))
                .thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob))
                .thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob))
                .thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }

}
