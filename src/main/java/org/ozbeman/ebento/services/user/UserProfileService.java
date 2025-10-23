package org.ozbeman.ebento.services.user;

import org.ozbeman.ebento.dto.session.UserProfileSessionDTO;
import org.ozbeman.ebento.dto.user.UpdateUserProfileDTO;
import org.ozbeman.ebento.dto.user.UserProfileDTO;
import org.ozbeman.ebento.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserProfileService {
    UserProfileDTO getUserProfile(User user);

    UserProfileDTO updateUserProfile(User user, UpdateUserProfileDTO dto);

    List<UserProfileSessionDTO> getUserProfileSessions(User user);

    void deleteUserProfileSession(UUID guid, User user);
}
