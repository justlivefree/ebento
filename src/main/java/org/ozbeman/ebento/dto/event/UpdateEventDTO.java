package org.ozbeman.ebento.dto.event;

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
public class UpdateEventDTO {
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDate endDate;
    private Integer hallSize;
}
