package org.ozbema.ebento.dto.channel.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.dto.category.CategoryDTO;
import org.ozbema.ebento.entity.Channel;
import org.ozbema.ebento.entity.enums.ChannelStatus;

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

    private AdminCreatorUserDTO user;

    private CategoryDTO category;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;


    public static AdminChannelDTO of(Channel channel) {
        return AdminChannelDTO.builder()
                .id(channel.getGuid())
                .title(channel.getTitle())
                .description(channel.getDescription())
                .status(channel.getStatus())
                .user(AdminCreatorUserDTO.of(channel.getUser()))
                .category(CategoryDTO.of(channel.getCategory()))
                .avatarFileId(channel.getAvatarFileId())
                .backgroundFileId(channel.getBackgroundFileId())
                .createdAt(channel.getCreatedAt())
                .build();
    }
}
