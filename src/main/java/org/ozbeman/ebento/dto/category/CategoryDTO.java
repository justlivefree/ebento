package org.ozbeman.ebento.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.ChannelCategory;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private UUID id;
    private String title;

    public static CategoryDTO of(ChannelCategory category) {
        if (category != null) {
            return CategoryDTO.builder()
                    .id(category.getGuid())
                    .title(category.getTitle())
                    .build();
        }
        return null;
    }
}
