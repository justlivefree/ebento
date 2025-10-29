package org.ozbeman.ebento.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.User;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private UUID guid;
    private String name;
    private String phoneNumber;
    private ProfileChannelDTO channel;
    private List<RoleDTO> roles;

    public static UserProfileDTO of(User user) {
        return UserProfileDTO.builder()
                .guid(user.getGuid())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .channel(ProfileChannelDTO.of(user.getChannel()))
                .roles(RoleDTO.of(user.getRoles()))
                .build();
    }
}
