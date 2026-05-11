package org.ozbema.ebento.dto.ticket.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.ozbema.ebento.entity.Ticket;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserTicketListDTO {
    private String serialNumber;
    private LocalDateTime createdAt;
    private Integer price;

    public static UserTicketListDTO of(Ticket ticket) {
        return new UserTicketListDTO(
                ticket.getSerialNumber(),
                ticket.getCreatedAt(),
                ticket.getPrice()
        );
    }
}
