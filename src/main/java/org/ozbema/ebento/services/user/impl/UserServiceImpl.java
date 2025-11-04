package org.ozbema.ebento.services.user.impl;

import org.ozbema.ebento.dto.session.DetailedSessionDTO;
import org.ozbema.ebento.dto.user.UserDTO;
import org.ozbema.ebento.dto.user.UserListDTO;
import org.ozbema.ebento.dto.user.UserSearchRequestDTO;
import org.ozbema.ebento.dto.user.UserUpdateDTO;
import org.ozbema.ebento.entity.User;
import org.ozbema.ebento.entity.enums.UserRole;
import org.ozbema.ebento.entity.enums.UserStatus;
import org.ozbema.ebento.exceptions.InvalidRequestException;
import org.ozbema.ebento.exceptions.ResourceNotFound;
import org.ozbema.ebento.repository.SessionRepository;
import org.ozbema.ebento.repository.UserRepository;
import org.ozbema.ebento.services.user.UserService;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public UserServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Page<UserListDTO> getUsers(UserSearchRequestDTO dto) {
        // todo: use Specifications
        PaginatedRequest paginatedRequest = dto.getPageRequest();
        Pageable pageable = paginatedRequest.generatePageable();
        String search = paginatedRequest.getSearch();
        UserRole role = dto.getRole();
        UserStatus status = dto.getStatus();
        Page<User> users;
        if (search != null) {
            users = userRepository.findByPhoneNumberContainsOrNameContainingIgnoreCase(search, search, pageable);
        } else if (role != null) {
            users = userRepository.findByRole(role, pageable);
        } else if (status != null) {
            users = userRepository.findByStatus(status, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users.map(UserListDTO::of);
    }

    @Override
    public UserDTO getUser(UUID guid) {
        return userRepository.findOneWithRolesAndChannelByGuid(guid)
                .map(UserDTO::of)
                .orElseThrow(() -> new ResourceNotFound("User Not Found"));
    }

    @Override
    @Transactional
    public UserDTO updateUser(UUID guid, UserUpdateDTO dto) {
        return userRepository.findOneWithRolesAndChannelByGuid(guid)
                .map(
                        user -> {
                            if (dto.getName() != null) {
                                user.setName(dto.getName());
                            }
                            String newPhoneNumber = dto.getPhoneNumber();
                            if (newPhoneNumber != null) {
                                if (!userRepository.existsUserByPhoneNumber(newPhoneNumber)) {
                                    throw new InvalidRequestException("Phone number already exists");
                                }
                                user.setPhoneNumber(newPhoneNumber);
                            }
                            if (dto.getStatus() != null) {
                                user.setStatus(dto.getStatus());
                            }
                            userRepository.save(user);
                            return UserDTO.of(user);
                        }
                )
                .orElseThrow(() -> new ResourceNotFound("User Not Found"));
    }

    @Override
    @Transactional
    public void deleteUser(UUID guid) {
        User user = userRepository.findOneWithRolesAndChannelByGuid(guid)
                .orElseThrow(() -> new ResourceNotFound("User Not Found"));
        userRepository.delete(user);
    }

    @Override
    public List<DetailedSessionDTO> getUserSessions(UUID guid) {
        return DetailedSessionDTO.of(sessionRepository.findByUserGuid(guid));
    }

    @Override
    @Transactional
    public void deleteSession(UUID guid) {
        if (sessionRepository.existsSessionByGuid(guid)) {
            sessionRepository.deleteByGuid(guid);
        }
        throw new ResourceNotFound("Session not found");
    }


}
