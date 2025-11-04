package org.ozbema.ebento.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.entity.enums.UserRole;
import org.ozbema.ebento.entity.enums.UserStatus;
import org.ozbema.ebento.utils.PaginatedRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchRequestDTO {
    private PaginatedRequest pageRequest;
    private UserRole role;
    private UserStatus status;
}
