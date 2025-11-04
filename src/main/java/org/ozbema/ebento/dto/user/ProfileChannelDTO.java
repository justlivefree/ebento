package org.ozbema.ebento.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.entity.Channel;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileChannelDTO {
    private UUID id;
    private String title;

    public static ProfileChannelDTO of(Channel channel) {
        if (channel != null) {
            return ProfileChannelDTO.builder()
                    .id(channel.getGuid())
                    .title(channel.getTitle())
                    .build();
        }
        return null;
    }
}
