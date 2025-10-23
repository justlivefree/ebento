package org.ozbeman.ebento.dto.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.enums.ChannelStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminChannelDTO {
    private UUID id;
    private String title;
    private String description;
    private ChannelStatus status;

    @JsonProperty("avatar_file_id")
    private UUID avatarFileId;

    @JsonProperty("background_file_id")
    private UUID backgroundFileId;

    @JsonProperty("active_events_count")
    private Integer activeEventsCount = 0;

    @JsonProperty("expired_events_count")
    private Integer expiredEventsCount = 0;

    @JsonProperty("canceled_events_count")
    private Integer canceledEventsCount = 0;

    private ChannelCreatorUserDTO user;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static AdminChannelDTO of(Channel channel) {
        return AdminChannelDTO.builder()
                .id(channel.getGuid())
                .title(channel.getTitle())
                .description(channel.getDescription())
                .status(channel.getStatus())
                .user(ChannelCreatorUserDTO.of(channel.getUser()))
                .avatarFileId(channel.getAvatarFileId())
                .backgroundFileId(channel.getBackgroundFileId())
                .createdAt(channel.getCreatedAt())
                .build();
    }
}
