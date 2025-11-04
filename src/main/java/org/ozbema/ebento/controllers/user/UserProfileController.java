package org.ozbema.ebento.controllers.user;

import jakarta.validation.Valid;
import org.ozbema.ebento.config.CustomUserDetails;
import org.ozbema.ebento.dto.session.UserProfileSessionDTO;
import org.ozbema.ebento.dto.user.UpdateUserProfileDTO;
import org.ozbema.ebento.dto.user.UserProfileDTO;
import org.ozbema.ebento.services.user.UserProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/me")
@PreAuthorize("isAuthenticated()")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/")
    public UserProfileDTO getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userProfileService.getUserProfile(userDetails.getUserModel());
    }

    @PatchMapping("/")
    public UserProfileDTO updateUserProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdateUserProfileDTO dto
    ) {
        return userProfileService.updateUserProfile(userDetails.getUserModel(), dto);
    }

    @GetMapping("/sessions")
    public List<UserProfileSessionDTO> getUserProfileSessions(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userProfileService.getUserProfileSessions(userDetails.getUserModel());
    }

    @DeleteMapping("sessions/{id}")
    public void deleteUserProfileSession(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userProfileService.deleteUserProfileSession(id, userDetails.getUserModel());
    }
}
