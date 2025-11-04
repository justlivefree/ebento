package org.ozbema.ebento.services.event;

import org.ozbema.ebento.dto.event.user.UserEventListDTO;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;

public interface UserEventService {
    Page<UserEventListDTO> getEvents(PaginatedRequest paginatedRequest);
}
