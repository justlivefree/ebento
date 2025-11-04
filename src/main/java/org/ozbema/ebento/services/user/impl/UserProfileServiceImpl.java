package org.ozbema.ebento.services.user.impl;

import org.ozbema.ebento.dto.session.UserProfileSessionDTO;
import org.ozbema.ebento.dto.user.UpdateUserProfileDTO;
import org.ozbema.ebento.dto.user.UserProfileDTO;
import org.ozbema.ebento.entity.Session;
import org.ozbema.ebento.entity.User;
import org.ozbema.ebento.entity.enums.SessionStatus;
import org.ozbema.ebento.exceptions.ResourceNotFound;
import org.ozbema.ebento.repository.SessionRepository;
import org.ozbema.ebento.repository.UserRepository;
import org.ozbema.ebento.services.user.UserProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public UserProfileServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    @Transactional
    public UserProfileDTO getUserProfile(User user) {
        return UserProfileDTO.of(user);
    }

    @Override
    public UserProfileDTO updateUserProfile(User user, UpdateUserProfileDTO dto) {
        user.setName(dto.getName());
        userRepository.save(user);
        return UserProfileDTO.of(user);
    }

    @Override
    public List<UserProfileSessionDTO> getUserProfileSessions(User user) {
        return UserProfileSessionDTO.of(sessionRepository.findByUserAndStatus(user, SessionStatus.ACTIVE));
    }

    @Override
    @Transactional
    public void deleteUserProfileSession(UUID guid, User user) {
        Session session = sessionRepository.findOneByGuidAndUser(guid, user)
                .orElseThrow(() -> new ResourceNotFound("Session Not Found"));
        sessionRepository.delete(session);
    }
}
