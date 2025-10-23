package org.ozbeman.ebento.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ozbeman.ebento.config.CustomUserDetails;
import org.ozbeman.ebento.dto.auth.AuthResponseDTO;
import org.ozbeman.ebento.dto.auth.LoginDTO;
import org.ozbeman.ebento.dto.auth.RegisterDTO;
import org.ozbeman.ebento.dto.auth.VerificationDTO;
import org.ozbeman.ebento.entity.Session;
import org.ozbeman.ebento.services.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO.getPhoneNumber());
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO.getName(), registerDTO.getPhoneNumber());
    }

    @PostMapping("/verify")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> verify(
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody VerificationDTO verificationDTO
    ) {
        authService.verify(userDetails, (Session) request.getAttribute("session"), verificationDTO.getCode());
        return ResponseEntity.accepted().build();
    }

}
