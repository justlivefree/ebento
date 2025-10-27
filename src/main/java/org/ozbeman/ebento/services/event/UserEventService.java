package org.ozbeman.ebento.services.event;

import org.ozbeman.ebento.dto.event.user.UserEventListDTO;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;

public interface UserEventService {
    Page<UserEventListDTO> getEvents(PaginatedRequest paginatedRequest);
}
