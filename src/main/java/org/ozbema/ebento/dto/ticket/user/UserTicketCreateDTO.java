package org.ozbema.ebento.dto.ticket.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTicketCreateDTO {
    @JsonProperty("event_id")
    private UUID eventId;

    @JsonProperty("user_id")
    private UUID userId;
}
