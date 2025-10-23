package org.ozbeman.ebento.dto.channel;

import jakarta.annotation.Nullable;
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
//    @Nullable
//    @Pattern(regexp = RegexPatternUtils.CHANNEL_TITLE)
    private String title;

//    @Nullable
//    @Size(min = 50, max = 255)
    private String description;
}
