package org.ozbema.ebento.dto.channel.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.utils.RegexPatternUtils;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminChannelCreateDTO {
    @NotBlank
    @Pattern(regexp = RegexPatternUtils.CHANNEL_TITLE)
    private String title;

    @NotBlank
    @Size(min = 50, max = 255)
    private String description;

    @NotBlank
    @JsonProperty("category_id")
    private UUID categoryId;

    @NotBlank
    @JsonProperty("user_id")
    private UUID userId;

}
