package org.ozbeman.ebento.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.enums.ChannelStatus;

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
