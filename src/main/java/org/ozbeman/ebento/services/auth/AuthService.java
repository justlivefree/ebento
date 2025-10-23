package org.ozbeman.ebento.services.auth;

import org.ozbeman.ebento.config.CustomUserDetails;
import org.ozbeman.ebento.dto.auth.AuthResponseDTO;
import org.ozbeman.ebento.entity.Role;
import org.ozbeman.ebento.entity.Session;
import org.ozbeman.ebento.entity.User;
import org.ozbeman.ebento.entity.enums.SessionStatus;
import org.ozbeman.ebento.entity.enums.UserRole;
import org.ozbeman.ebento.entity.enums.UserStatus;
import org.ozbeman.ebento.exceptions.InvalidOtpCodeException;
import org.ozbeman.ebento.exceptions.InvalidRequestException;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.user.RoleRepository;
import org.ozbeman.ebento.repository.user.SessionRepository;
import org.ozbeman.ebento.repository.user.UserRepository;
import org.ozbeman.ebento.services.OtpStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final JwtTokenService jwtTokenService;
    private final OtpStoreService otpStoreService;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, JwtTokenService jwtTokenService, OtpStoreService otpStoreService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.jwtTokenService = jwtTokenService;
        this.otpStoreService = otpStoreService;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public AuthResponseDTO login(String phoneNumber) {
        return userRepository.findOneByPhoneNumber(phoneNumber)
                .map(
                        user -> {
                            Session session = sessionRepository.save(Session.builder()
                                    .user(user)
                                    .status(SessionStatus.NOT_VERIFIED)
                                    .build());
                            String code = String.valueOf(new Random().nextInt(1000, 9999));
                            otpStoreService.save(String.valueOf(user.getId()), code);
                            String token = jwtTokenService.generateToken(session.getGuid().toString(), Map.of("roles", user.getRoles().stream().map(Role::getRole).toList()));
                            return new AuthResponseDTO(token, "refresh");
                        }
                ).orElseThrow(
                        () -> new ResourceNotFound("User Not found")
                );
    }

    public AuthResponseDTO register(String name, String phoneNumber) {
        Optional<User> user = userRepository.findOneByPhoneNumber(phoneNumber);
        if (user.isEmpty()) {
            User newUser = userRepository.save(User.builder()
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .status(UserStatus.NOT_VERIFIED)
                    .build()
            );
            roleRepository.save(Role.builder().user(newUser).role(UserRole.ROLE_USER).build());
            Session session = sessionRepository.save(Session.builder()
                    .user(newUser)
                    .status(SessionStatus.NOT_VERIFIED)
                    .build());
            String token = jwtTokenService.generateToken(session.getGuid().toString(), Map.of("roles", List.of(UserRole.ROLE_USER)));
            String code = String.valueOf(new Random().nextInt(1000, 9999));
            otpStoreService.save(String.valueOf(newUser.getId()), code);
            return new AuthResponseDTO(token, "refresh");
        }
        throw new InvalidRequestException("User Already Exists");
    }

    public void verify(CustomUserDetails userDetails, Session session, String code) {
        User user = userDetails.getUserModel();
        String savedCode = otpStoreService.get(String.valueOf(user.getId()));
        if (savedCode == null) {
            throw new InvalidOtpCodeException("Code is expired");
        }
        if (!savedCode.equals(code)) {
            throw new InvalidOtpCodeException("Invalid code");
        }
        if (user.getStatus() == UserStatus.NOT_VERIFIED) {
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
        }
        session.setStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);
    }
}
