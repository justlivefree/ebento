package org.ozbema.ebento.dto.event.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminEventCreateUpdateDTO {
    private String title;

    private String description;

    @JsonProperty("start_date")
    private LocalDateTime startDateTime;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("hall_size")
    private Integer hallSize;

    private Double lat;

    private Double lon;

    @JsonProperty("channel_id")
    private UUID channelId;
}
