package org.ozbeman.ebento.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventDTO {
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDate endDate;
    private Integer hallSize;
}
