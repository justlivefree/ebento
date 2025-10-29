package org.ozbeman.ebento.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.ozbeman.ebento.entity.Session;
import org.ozbeman.ebento.entity.User;
import org.ozbeman.ebento.entity.enums.SessionStatus;
import org.ozbeman.ebento.repository.SessionRepository;
import org.ozbeman.ebento.utils.ApiErrorResponse;
import org.ozbeman.ebento.services.auth.JwtTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private static final ObjectMapper mapper = new ObjectMapper();

    public JwtAuthFilter(JwtTokenService jwtTokenService, SessionRepository sessionRepository) {
        this.jwtTokenService = jwtTokenService;
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7);
                UUID sessionGuid = UUID.fromString(jwtTokenService.extractSessionId(token));
                List<SimpleGrantedAuthority> authorities = jwtTokenService.extractRoles(token).stream().map(SimpleGrantedAuthority::new).toList();
                Session session = sessionRepository.findOneWithUserByGuid(sessionGuid);
                request.setAttribute("session", session);
                if (session != null && session.getStatus() == SessionStatus.ACTIVE) {
                    User userModel = session.getUser();
                    UserDetails userDetails = new CustomUserDetails(userModel);
                    AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            String errorResponseJson = mapper.writeValueAsString(new ApiErrorResponse("Invalid Token"));
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(errorResponseJson);
        }
    }
}
