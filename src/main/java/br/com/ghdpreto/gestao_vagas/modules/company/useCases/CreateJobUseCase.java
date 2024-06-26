package br.com.ghdpreto.gestao_vagas.modules.company.useCases;

import br.com.ghdpreto.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.ghdpreto.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ghdpreto.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ghdpreto.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity) {

        this.companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> new CompanyNotFoundException());

        return this.jobRepository.save(jobEntity);

    }

}
