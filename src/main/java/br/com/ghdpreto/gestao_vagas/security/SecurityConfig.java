package br.com.ghdpreto.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
// ativa para usar o hasRole nos controller (grants)
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;

    // informa para usar esse objeto ao inves de usar o nativo do spring
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // desabilita a config default do security
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    // rotas abertas
                    auth
                            // candidate
                            .requestMatchers("/candidate").permitAll()
                            .requestMatchers("/candidate/profile").permitAll()
                            .requestMatchers("/candidate/auth").permitAll()

                            // company
                            .requestMatchers("/company").permitAll()
                            .requestMatchers("/company/job").permitAll()
                            .requestMatchers("/company/auth").permitAll()

                            // bloqueio
                            .anyRequest().authenticated();
                })
                // middleware
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    // criptografia de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
