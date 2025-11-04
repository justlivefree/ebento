package org.ozbema.ebento.dto.channel.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.dto.category.CategoryDTO;
import org.ozbema.ebento.entity.Channel;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChannelDTO {
    private UUID id;

    private String title;

    private String description;

    private CategoryDTO category;

    @JsonProperty("avatar_file_id")
    private UUID avatarFileId;

    @JsonProperty("background_file_id")
    private UUID backgroundFileId;

    public static UserChannelDTO of(Channel channel) {
        return UserChannelDTO.builder()
                .id(channel.getGuid())
                .title(channel.getTitle())
                .description(channel.getDescription())
                .category(CategoryDTO.of(channel.getCategory()))
                .avatarFileId(channel.getAvatarFileId())
                .backgroundFileId(channel.getBackgroundFileId())
                .build();
    }

}
