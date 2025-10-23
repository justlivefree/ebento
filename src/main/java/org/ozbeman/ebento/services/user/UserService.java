package org.ozbeman.ebento.services.user;

import org.ozbeman.ebento.dto.session.DetailedSessionDTO;
import org.ozbeman.ebento.dto.user.UserDTO;
import org.ozbeman.ebento.dto.user.UserListDTO;
import org.ozbeman.ebento.dto.user.UserSearchRequestDTO;
import org.ozbeman.ebento.dto.user.UserUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserService {

    Page<UserListDTO> getUsers(UserSearchRequestDTO userSearchRequestDTO);

    UserDTO getUser(UUID guid);

    UserDTO updateUser(UUID guid, UserUpdateDTO dto);

    void deleteUser(UUID guid);

    void deleteSession(UUID guid);

    List<DetailedSessionDTO> getUserSessions(UUID guid);
}
