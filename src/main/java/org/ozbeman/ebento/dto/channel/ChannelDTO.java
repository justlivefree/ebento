package org.ozbeman.ebento.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.enums.ChannelStatus;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO {
    private UUID id;
    private String title;
    private ChannelStatus status;

    public static ChannelDTO of(Channel channel) {
        if (channel != null) {
            return ChannelDTO.builder()
                    .id(channel.getGuid())
                    .title(channel.getTitle())
                    .status(channel.getStatus())
                    .build();
        }
        return null;
    }

}
