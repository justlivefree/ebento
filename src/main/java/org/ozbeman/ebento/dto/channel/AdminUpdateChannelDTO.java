package org.ozbeman.ebento.dto.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.enums.ChannelStatus;
import org.ozbeman.ebento.utils.RegexPatternUtils;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateChannelDTO {

    @Nullable
    @Pattern(regexp = RegexPatternUtils.CHANNEL_TITLE)
    private String title;

    @Nullable
    @Size(min = 50, max = 255)
    private String description;

    private ChannelStatus status;

    @JsonProperty("user_id")
    private UUID userId;
}
