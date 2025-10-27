package org.ozbeman.ebento.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.dto.channel.ChannelDTO;
import org.ozbeman.ebento.entity.User;
import org.ozbeman.ebento.entity.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String phoneNumber;
    private UserStatus status;
    private List<RoleDTO> roles;
    private ChannelDTO channel;
    private LocalDateTime createdAt;

    public static UserDTO of(User user) {
        return UserDTO.builder()
                .id(user.getGuid())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .roles(RoleDTO.of(user.getRoles()))
                .channel(ChannelDTO.of(user.getChannel()))
                .build();
    }

    public static List<UserDTO> of(List<User> users) {
        return users.stream().map(UserDTO::of).toList();
    }

}
