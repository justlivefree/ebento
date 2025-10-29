package org.ozbeman.ebento.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ozbeman.ebento.entity.enums.UserStatus;
import org.ozbeman.ebento.utils.RegexPatternUtils;

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
