package org.ozbeman.ebento.dto.channel.creator;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.utils.RegexPatternUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorUpdateChannelDTO {
    @Pattern(regexp = RegexPatternUtils.CHANNEL_TITLE)
    private String title;

    @Size(min = 50, max = 255)
    private String description;
}
