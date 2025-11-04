package org.ozbema.ebento.dto.event.creator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatorCreateUpdateEventDTO {
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

}
