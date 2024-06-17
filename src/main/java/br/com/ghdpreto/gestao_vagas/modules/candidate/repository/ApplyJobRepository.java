package br.com.ghdpreto.gestao_vagas.modules.candidate.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ghdpreto.gestao_vagas.modules.candidate.entitie.ApplyJobEntity;

@Repository
public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
    
}
