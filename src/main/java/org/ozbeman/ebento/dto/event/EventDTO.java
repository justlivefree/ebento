package org.ozbeman.ebento.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.dto.channel.ChannelDTO;
import org.ozbeman.ebento.entity.Event;
import org.ozbeman.ebento.entity.enums.EventStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDate endDate;
    private Integer hallSize;
    private ChannelDTO channel;
    private EventStatus status;
    private UUID fileId;

    public static EventDTO of(Event event) {
        return EventDTO.builder()
                .id(event.getGuid())
                .title(event.getTitle())
                .description(event.getDescription())
                .startDateTime(event.getStartDateTime())
                .endDate(event.getEndDate())
                .channel(ChannelDTO.of(event.getChannel()))
                .status(event.getStatus())
                .fileId(event.getFileId())
                .build();
    }
}
