package org.ozbeman.ebento.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventListDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDate endDate;
    private Integer hallSize;
    private UUID fileId;

    public static EventListDTO of(Event event) {
        return EventListDTO.builder()
                .id(event.getGuid())
                .title(event.getTitle())
                .description(event.getDescription())
                .startDateTime(event.getStartDateTime())
                .endDate(event.getEndDate())
                .hallSize(event.getHallSize())
                .fileId(event.getFileId())
                .build();
    }


}
