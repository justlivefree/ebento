package org.ozbeman.ebento.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.utils.RegexPatternUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @JsonProperty(value = "phone_number", required = true)
//    @Pattern(regexp = RegexPatternUtils.PHONE_NUMBER)
    private String phoneNumber;
}
