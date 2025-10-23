package org.ozbeman.ebento.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.enums.UserRole;
import org.ozbeman.ebento.entity.enums.UserStatus;
import org.ozbeman.ebento.utils.PaginatedRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchRequestDTO {
    private PaginatedRequest pageRequest;
    private UserRole role;
    private UserStatus status;
}
