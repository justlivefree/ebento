package org.ozbema.ebento.controllers.ticket;

import jakarta.validation.Valid;
import org.ozbema.ebento.dto.ticket.user.UserTicketCreateDTO;
import org.ozbema.ebento.entity.Event;
import org.ozbema.ebento.entity.Ticket;
import org.ozbema.ebento.entity.User;
import org.ozbema.ebento.entity.enums.TicketStatus;
import org.ozbema.ebento.repository.EventRepository;
import org.ozbema.ebento.repository.TicketRepository;
import org.ozbema.ebento.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/ticket")
public class UserTicketController {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public UserTicketController(EventRepository eventRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("")
    public void getAllTickets() {
        Event event = eventRepository.findById(1L).get();
        User user = userRepository.findById(1L).get();
        Ticket ticket = Ticket.builder()
                .event(event)
                .user(user)
                .status(TicketStatus.ACTIVE)
                .build();
        ticketRepository.save(ticket);
    }

    @PostMapping("")
    public void createTicket(@Valid @RequestBody UserTicketCreateDTO userTicketCreateDTO) {

    }
}
