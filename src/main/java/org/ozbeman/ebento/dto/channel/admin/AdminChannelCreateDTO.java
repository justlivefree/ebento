package org.ozbeman.ebento.dto.channel.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.utils.RegexPatternUtils;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminChannelCreateDTO {
    @NotBlank
    @Pattern(regexp = RegexPatternUtils.CHANNEL_TITLE)
    private String title;

    @NotBlank
    @Pattern(regexp = RegexPatternUtils.CHANNEL_DESCRIPTION)
    private String description;

    @JsonProperty("category_id")
    private UUID categoryId;

    @JsonProperty("user_id")
    private UUID userId;

}
