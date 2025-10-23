package org.ozbeman.ebento.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.User;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelCreatorUserDTO {
    private UUID id;
    private String name;

    public static ChannelCreatorUserDTO of(User user) {
        return ChannelCreatorUserDTO.builder()
                .id(user.getGuid())
                .name(user.getName())
                .build();
    }
}
