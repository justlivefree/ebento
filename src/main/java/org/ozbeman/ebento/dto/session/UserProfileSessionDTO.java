package org.ozbeman.ebento.dto.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.Session;
import org.ozbeman.ebento.entity.enums.SessionStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileSessionDTO {
    private UUID id;
    private String device;
    @JsonProperty("created_at")
    private LocalDate createdAt;

    public static UserProfileSessionDTO of(Session session) {
        return UserProfileSessionDTO.builder()
                .id(session.getGuid())
                .device(session.getDeviceName())
                .createdAt(session.getCreatedAt().toLocalDate())
                .build();
    }

    public static List<UserProfileSessionDTO> of(List<Session> sessions) {
        return sessions.stream().map(UserProfileSessionDTO::of).toList();
    }

}
