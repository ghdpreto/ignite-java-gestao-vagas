package br.com.ghdpreto.gestao_vagas.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ghdpreto.gestao_vagas.modules.company.entities.JobEntity;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    // "contains - LIKE do sql"
    // SELCT * FROM JOB WHERE DESCRIPTION LIKE(filter);
    List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);

}
