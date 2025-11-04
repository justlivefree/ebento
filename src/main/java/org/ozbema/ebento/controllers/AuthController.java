package org.ozbema.ebento.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ozbema.ebento.dto.auth.AuthResponseDTO;
import org.ozbema.ebento.dto.auth.LoginDTO;
import org.ozbema.ebento.dto.auth.RegisterDTO;
import org.ozbema.ebento.dto.auth.VerificationDTO;
import org.ozbema.ebento.entity.Session;
import org.ozbema.ebento.services.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("permitAll()")
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
    public ResponseEntity<Void> verify(
            HttpServletRequest request,
            @Valid @RequestBody VerificationDTO verificationDTO
    ) {
        authService.verify((Session) request.getAttribute("session"), verificationDTO.getCode());
        return ResponseEntity.accepted().build();
    }

}
