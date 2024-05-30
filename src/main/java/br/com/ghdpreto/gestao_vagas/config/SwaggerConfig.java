package br.com.ghdpreto.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

// // config inicial do swagger
// @OpenAPIDefinition(info = @Info(title = "Gestão de vagas", description = "API Responsável pela gestão de vagas", version = "1"))
// // configurando auth no swagger
// @SecurityScheme(name = "jwt_auth", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        createInfoApi())
                .schemaRequirement("jwt_auth", createSecurityScheme());
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name("jwt_auth")
                .scheme("bearer")
                .bearerFormat("JWT")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER);
    }

    private Info createInfoApi() {
        return new Info()
                .title("Gestão de vagas")
                .description("API Responsável pela gestão de vagas")
                .version("1");
    }
}
