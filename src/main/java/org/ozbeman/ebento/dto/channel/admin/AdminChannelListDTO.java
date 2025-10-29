package org.ozbeman.ebento.dto.channel.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.dto.category.CategoryDTO;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.enums.ChannelStatus;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminChannelListDTO {
    private UUID id;
    private String title;
    private ChannelStatus status;
    private CategoryDTO category;

    public static AdminChannelListDTO of(Channel channel) {
        if (channel != null) {
            return AdminChannelListDTO.builder()
                    .id(channel.getGuid())
                    .title(channel.getTitle())
                    .status(channel.getStatus())
                    .category(CategoryDTO.of(channel.getCategory()))
                    .build();
        }
        return null;
    }

}
