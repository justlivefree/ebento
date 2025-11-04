package org.ozbema.ebento.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ozbema.ebento.entity.enums.UserStatus;
import org.ozbema.ebento.utils.RegexPatternUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    @Pattern(regexp = RegexPatternUtils.USER_NAME)
    private String name;

    @JsonProperty("phone_number")
    @Pattern(regexp = RegexPatternUtils.PHONE_NUMBER)
    private String phoneNumber;

    private UserStatus status;
}
