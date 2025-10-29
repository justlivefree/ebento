package org.ozbeman.ebento.dto.channel.creator;

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
public class CreatorChannelDTO {
    private UUID id;

    private String title;

    private String description;

    private ChannelStatus status;

    @JsonProperty("avatar_file_id")
    private UUID avatarFileId;

    @JsonProperty("background_file_id")
    private UUID backgroundFileId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static CreatorChannelDTO of(Channel channel) {
        return CreatorChannelDTO.builder()
                .id(channel.getGuid())
                .title(channel.getTitle())
                .description(channel.getDescription())
                .status(channel.getStatus())
                .avatarFileId(channel.getAvatarFileId())
                .backgroundFileId(channel.getBackgroundFileId())
                .createdAt(channel.getCreatedAt())
                .build();
    }
}
