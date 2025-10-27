package org.ozbeman.ebento.dto.event.creator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.Event;
import org.ozbeman.ebento.entity.enums.EventStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatorEventListDTO {
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

    public static CreatorEventListDTO of(Event event) {
        return CreatorEventListDTO.builder()
                .id(event.getGuid())
                .title(event.getTitle())
                .description(event.getDescription())
                .startDateTime(event.getStartDateTime())
                .endDate(event.getEndDate())
                .hallSize(event.getHallSize())
                .fileId(event.getFileId())
                .status(event.getStatus())
                .build();
    }


}
