package org.ozbema.ebento.dto.event.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbema.ebento.dto.channel.ChannelDTO;
import org.ozbema.ebento.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEventListDTO {
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

    private ChannelDTO channel;

    private Double lat;

    private Double lon;

    public static UserEventListDTO of(Event event) {
        return UserEventListDTO.builder()
                .id(event.getGuid())
                .title(event.getTitle())
                .description(event.getDescription())
                .startDateTime(event.getStartDateTime())
                .endDate(event.getEndDate())
                .hallSize(event.getHallSize())
                .fileId(event.getFileId())
                .channel(ChannelDTO.of(event.getChannel()))
                .lat(event.getLat())
                .lon(event.getLon())
                .build();
    }


}
