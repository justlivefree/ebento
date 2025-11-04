package org.ozbema.ebento.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.utils.RegexPatternUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @Pattern(regexp = RegexPatternUtils.PHONE_NUMBER)
    @JsonProperty(value = "phone_number", required = true)
    private String phoneNumber;

    @Pattern(regexp = RegexPatternUtils.USER_NAME)
    @JsonProperty(required = true)
    private String name;
}
