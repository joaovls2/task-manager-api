package com.joaovictor.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.joaovictor.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if(servletPath.startsWith("/tasks/")) {

            // Pegar a autenticação (Usuário e senha)
            var authorization = request.getHeader("Authorization");

            // Valida header
            if (authorization == null || !authorization.startsWith("Basic ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            var authEncoded = authorization.substring("Basic".length()).trim();

            try {
                byte[] authDecode = Base64.getDecoder().decode(authEncoded);
                var authString = new String(authDecode);

                String[] credentials = authString.split(":");

                if (credentials.length != 2) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                    return;
                }

                String username = credentials[0];
                String password = credentials[1];

                var user = this.userRepository.findByUsername(username);

                if (user == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                    return;
                }

                var passwordVerify = BCrypt.verifyer()
                        .verify(password.toCharArray(), user.getPassword());

                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                }

            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}