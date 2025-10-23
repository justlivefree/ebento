package org.ozbeman.ebento.dto.auth;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.utils.RegexPatternUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationDTO {
    @Pattern(regexp = RegexPatternUtils.VARIFICATION_CODE)
    private String code;
}
