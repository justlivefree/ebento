package org.ozbema.ebento.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.entity.Role;
import org.ozbema.ebento.entity.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private UUID id;
    private UserRole role;
    private LocalDateTime createdAt;

    public static RoleDTO of(Role role) {
        return RoleDTO.builder()
                .id(role.getGuid())
                .role(role.getRole())
                .createdAt(role.getCreatedAt())
                .build();
    }

    public static List<RoleDTO> of(List<Role> roles) {
        return roles.stream().map(RoleDTO::of).toList();
    }
}
