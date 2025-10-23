package org.ozbeman.ebento.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ozbeman.ebento.config.CustomUserDetails;
import org.ozbeman.ebento.entity.Session;
import org.ozbeman.ebento.entity.User;
import org.ozbeman.ebento.entity.enums.SessionStatus;
import org.ozbeman.ebento.repository.user.SessionRepository;
import org.ozbeman.ebento.services.auth.JwtTokenService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final SessionRepository sessionRepository;

    public JwtAuthFilter(JwtTokenService jwtTokenService, SessionRepository sessionRepository) {
        this.jwtTokenService = jwtTokenService;
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);
            if (jwtTokenService.isValid(token)) {
                UUID sessionGuid = UUID.fromString(jwtTokenService.extractSessionId(token));
                List<SimpleGrantedAuthority> authorities = jwtTokenService.extractRoles(token).stream().map(SimpleGrantedAuthority::new).toList();
                Session session = sessionRepository.findOneWithUserByGuid(sessionGuid);
                if (session != null) {
                    User userModel = session.getUser();
                    SessionStatus sessionStatus = session.getStatus();
                    if (
                            (request.getRequestURI().equals("/api/v1/auth/verify") && sessionStatus == SessionStatus.NOT_VERIFIED) ||
                            (!request.getRequestURI().equals("/api/v1/auth/verify") && sessionStatus == SessionStatus.ACTIVE)
                    ) {
                        UserDetails userDetails = new CustomUserDetails(userModel);
                        request.setAttribute("session", session);
                        AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

            }
        }
        filterChain.doFilter(request, response);
    }
}
