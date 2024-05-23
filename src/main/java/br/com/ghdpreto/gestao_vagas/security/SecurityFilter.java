package br.com.ghdpreto.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ghdpreto.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    // filtro para pegar o token auth
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // zerando o contexto do usuario
        SecurityContextHolder.getContext().setAuthentication(null);

        String header = request.getHeader("Authorization");
        System.out.println(header);

        // validar o token que foi recebido
        if (header != null) {

            var subjectToken = this.jwtProvider.validateToken(header);

            if (subjectToken.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // add o id no request
            request.setAttribute("company_id", subjectToken);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken, null,
                    Collections.emptyList());

            // criando o contexto
            SecurityContextHolder.getContext().setAuthentication(auth);

        }

        filterChain.doFilter(request, response);
    }

}
