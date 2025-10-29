package org.ozbeman.ebento.dto.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.Session;
import org.ozbeman.ebento.entity.enums.SessionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedSessionDTO {
    private UUID id;

    private String device;

    private SessionStatus status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static DetailedSessionDTO of(Session session) {
        return DetailedSessionDTO.builder()
                .id(session.getGuid())
                .device(session.getDeviceName())
                .status(session.getStatus())
                .createdAt(session.getCreatedAt())
                .build();
    }

    public static List<DetailedSessionDTO> of(List<Session> sessions) {
        return sessions.stream().map(DetailedSessionDTO::of).toList();
    }

}
