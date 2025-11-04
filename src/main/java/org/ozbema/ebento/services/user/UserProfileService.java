package org.ozbema.ebento.services.user;

import org.ozbema.ebento.dto.session.UserProfileSessionDTO;
import org.ozbema.ebento.dto.user.UpdateUserProfileDTO;
import org.ozbema.ebento.dto.user.UserProfileDTO;
import org.ozbema.ebento.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserProfileService {
    UserProfileDTO getUserProfile(User user);

    UserProfileDTO updateUserProfile(User user, UpdateUserProfileDTO dto);

    List<UserProfileSessionDTO> getUserProfileSessions(User user);

    void deleteUserProfileSession(UUID guid, User user);
}
