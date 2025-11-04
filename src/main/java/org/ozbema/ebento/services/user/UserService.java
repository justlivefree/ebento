package org.ozbema.ebento.services.user;

import org.ozbema.ebento.dto.session.DetailedSessionDTO;
import org.ozbema.ebento.dto.user.UserDTO;
import org.ozbema.ebento.dto.user.UserListDTO;
import org.ozbema.ebento.dto.user.UserSearchRequestDTO;
import org.ozbema.ebento.dto.user.UserUpdateDTO;
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
