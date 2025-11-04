package org.ozbema.ebento.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.entity.User;
import org.ozbema.ebento.entity.enums.UserStatus;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {
    private UUID id;

    private String name;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    private UserStatus status;

    public static UserListDTO of(User user) {
        return UserListDTO.builder()
                .id(user.getGuid())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .build();
    }

    public static List<UserListDTO> of(List<User> users) {
        return users.stream().map(UserListDTO::of).toList();
    }

}
