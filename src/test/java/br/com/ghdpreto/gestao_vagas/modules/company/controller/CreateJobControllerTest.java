package br.com.ghdpreto.gestao_vagas.modules.company.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.ghdpreto.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.ghdpreto.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.ghdpreto.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.ghdpreto.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.ghdpreto.gestao_vagas.utils.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // usar o application-test
public class CreateJobControllerTest {

        private MockMvc mvc;

        @Autowired
        private WebApplicationContext context;

        @Autowired
        private CompanyRepository companyRepository;

        @Before
        public void setup() {
                mvc = MockMvcBuilders.webAppContextSetup(context)
                                .apply(SecurityMockMvcConfigurers.springSecurity())
                                .build();
        }

        @Test
        public void should_be_able_to_create_a_new_job() throws Exception {
                CompanyEntity company = CompanyEntity.builder()
                                .name("COMPANY_NAME")
                                .description("COMPANY_DESCRIPTION")
                                .username("COMPANY_USERNAME")
                                .email("EMAIL@COMPANY.COM")
                                .password("1234567890@123")
                                .build();

                company = this.companyRepository.saveAndFlush(company); // salva de imediato

                var authToken = TestUtils.generateToken(company.getId());
                System.out.println(authToken);

                CreateJobDTO createdJobDTO = CreateJobDTO
                                .builder()
                                .benefits("BENEFITS_TEST")
                                .description("DESCRIPTION_TEST")
                                .level("LEVEL_TEST")
                                .build();

                // AQUI ESTA GERANDO ERRO DE CONTEXT
                var result = mvc.perform(
                                MockMvcRequestBuilders
                                                .post("/company/job")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("Authorization", TestUtils.generateToken(company.getId()))
                                                .content(TestUtils.objectToJson(createdJobDTO)))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                System.out.println(result);

        }

        @Test
        public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception{
                CreateJobDTO createdJobDTO = CreateJobDTO
                        .builder()
                        .benefits("BENEFITS_TEST")
                        .description("DESCRIPTION_TEST")
                        .level("LEVEL_TEST")
                        .build();

                        mvc.perform(
                                MockMvcRequestBuilders
                                        .post("/company/job")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .header("Authorization", TestUtils.generateToken(UUID.randomUUID()))
                                        .content(TestUtils.objectToJson(createdJobDTO)))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());


        }

}
