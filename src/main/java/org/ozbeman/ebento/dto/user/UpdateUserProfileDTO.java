package org.ozbeman.ebento.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileDTO {
    @NotBlank(message = "Name field is blank")
    @Size(min = 5, max = 25, message = "Name field is too long")
    private String name;
}
