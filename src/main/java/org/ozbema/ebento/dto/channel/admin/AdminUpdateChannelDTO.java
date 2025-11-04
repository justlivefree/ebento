package org.ozbema.ebento.dto.channel.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.entity.enums.ChannelStatus;
import org.ozbema.ebento.utils.RegexPatternUtils;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateChannelDTO {
    @Pattern(regexp = RegexPatternUtils.CHANNEL_TITLE)
    private String title;

    @Size(min = 50, max = 255)
    private String description;

    private ChannelStatus status;

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("category_id")
    private UUID categoryId;
}
