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
public class UserChannelListDTO {
    private UUID id;

    private String title;

    private CategoryDTO category;

    @JsonProperty("avatar_file_id")
    private UUID avatarFileId;

    public static UserChannelListDTO of(Channel channel) {
        return UserChannelListDTO.builder()
                .id(channel.getGuid())
                .title(channel.getTitle())
                .category(CategoryDTO.of(channel.getCategory()))
                .avatarFileId(channel.getAvatarFileId())
                .build();
    }
}
