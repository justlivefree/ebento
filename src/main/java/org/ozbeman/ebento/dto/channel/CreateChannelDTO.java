package org.ozbeman.ebento.dto.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.utils.RegexPatternUtils;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChannelDTO {
    @Pattern(regexp = RegexPatternUtils.CHANNEL_TITLE)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String description;

    @JsonProperty("user_id")
    private UUID userId;
}
