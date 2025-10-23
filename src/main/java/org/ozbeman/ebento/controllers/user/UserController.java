package org.ozbeman.ebento.controllers.user;

import jakarta.validation.Valid;
import org.ozbeman.ebento.dto.session.DetailedSessionDTO;
import org.ozbeman.ebento.dto.user.UserDTO;
import org.ozbeman.ebento.dto.user.UserListDTO;
import org.ozbeman.ebento.dto.user.UserSearchRequestDTO;
import org.ozbeman.ebento.dto.user.UserUpdateDTO;
import org.ozbeman.ebento.entity.enums.UserRole;
import org.ozbeman.ebento.entity.enums.UserStatus;
import org.ozbeman.ebento.services.user.UserService;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.ozbeman.ebento.utils.PaginatedResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/api/v1/user")
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public PaginatedResponse<UserListDTO> getUserList(
            @Valid PaginatedRequest paginatedRequest,
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) UserStatus status
    ) {
        return PaginatedResponse.of(userService.getUsers(
                new UserSearchRequestDTO(paginatedRequest, role, status))
        );
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") UUID id) {
        return userService.getUser(id);
    }

    @PatchMapping("/{id}")
    public UserDTO updateUser(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(id, userUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}/sessions")
    public List<DetailedSessionDTO> getUserSessions(@PathVariable UUID id) {
        return userService.getUserSessions(id);
    }

    @DeleteMapping("/sessions/{id}")
    public void deleteUserSession(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

}
