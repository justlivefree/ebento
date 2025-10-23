package org.ozbeman.ebento.dto.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ChannelListDTO {
    private UUID id;
    private String title;
    private ChannelStatus status;
    @JsonProperty("events_count")
    private Integer eventsCount;

    public static ChannelListDTO of(Channel channel) {
        return ChannelListDTO.builder()
                .id(channel.getGuid())
                .title(channel.getTitle())
                .status(channel.getStatus())
                .build();
    }
}
