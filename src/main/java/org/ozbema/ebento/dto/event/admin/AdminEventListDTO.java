package org.ozbema.ebento.dto.event.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.dto.channel.ChannelDTO;
import org.ozbema.ebento.entity.Event;
import org.ozbema.ebento.entity.enums.EventStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminEventListDTO {
    private UUID id;

    private String title;

    private String description;

    @JsonProperty("start_date")
    private LocalDateTime startDateTime;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("hall_size")
    private Integer hallSize;

    @JsonProperty("file_id")
    private UUID fileId;

    private EventStatus status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    private ChannelDTO channel;

    private Double lat;

    private Double lon;

    public static AdminEventListDTO of(Event event) {
        return AdminEventListDTO.builder()
                .id(event.getGuid())
                .title(event.getTitle())
                .description(event.getDescription())
                .startDateTime(event.getStartDateTime())
                .endDate(event.getEndDate())
                .hallSize(event.getHallSize())
                .fileId(event.getFileId())
                .status(event.getStatus())
                .createdAt(event.getCreatedAt())
                .channel(ChannelDTO.of(event.getChannel()))
                .lat(event.getLat())
                .lon(event.getLon())
                .build();
    }
}
