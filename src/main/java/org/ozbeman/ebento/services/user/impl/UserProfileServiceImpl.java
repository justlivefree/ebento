package org.ozbeman.ebento.services.user.impl;

import org.ozbeman.ebento.dto.session.UserProfileSessionDTO;
import org.ozbeman.ebento.dto.user.UpdateUserProfileDTO;
import org.ozbeman.ebento.dto.user.UserProfileDTO;
import org.ozbeman.ebento.entity.Session;
import org.ozbeman.ebento.entity.User;
import org.ozbeman.ebento.entity.enums.SessionStatus;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.SessionRepository;
import org.ozbeman.ebento.repository.UserRepository;
import org.ozbeman.ebento.services.user.UserProfileService;
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
