package org.ozbema.ebento.dto.channel.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.entity.User;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreatorUserDTO {
    private UUID id;
    private String name;

    public static AdminCreatorUserDTO of(User user) {
        return AdminCreatorUserDTO.builder()
                .id(user.getGuid())
                .name(user.getName())
                .build();
    }
}
