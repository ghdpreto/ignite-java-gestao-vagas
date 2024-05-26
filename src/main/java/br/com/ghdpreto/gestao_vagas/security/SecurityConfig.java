package br.com.ghdpreto.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    // informa para usar esse objeto ao inves de usar o nativo do spring
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // desabilita a config default do security
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    // rotas abertas
                    auth.requestMatchers("/candidate").permitAll()
                            .requestMatchers("/auth/candidate").permitAll()
                            .requestMatchers("/auth/company").permitAll()
                            .requestMatchers("/company").permitAll()
                            .anyRequest().authenticated();
                })
                // middleware
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)

        ;

        return http.build();
    }

    // criptografia de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
