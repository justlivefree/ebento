package org.ozbeman.ebento.controllers.event;

import jakarta.validation.Valid;
import org.ozbeman.ebento.dto.event.user.UserEventListDTO;
import org.ozbeman.ebento.services.event.UserEventService;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.ozbeman.ebento.utils.PaginatedResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/event")
@PreAuthorize("permitAll()")
public class UserEventController {
    private final UserEventService userEventService;

    public UserEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @GetMapping("")
    public PaginatedResponse<UserEventListDTO> getEvents(@Valid PaginatedRequest paginatedRequest) {
        return PaginatedResponse.of(userEventService.getEvents(paginatedRequest));
    }
}
