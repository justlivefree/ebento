package org.ozbema.ebento.services.event.impl;

import org.ozbema.ebento.dto.event.user.UserEventListDTO;
import org.ozbema.ebento.entity.enums.EventStatus;
import org.ozbema.ebento.repository.EventRepository;
import org.ozbema.ebento.services.event.UserEventService;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserEventServiceImpl implements UserEventService {
    private final EventRepository eventRepository;

    public UserEventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Page<UserEventListDTO> getEvents(PaginatedRequest paginatedRequest) {
        Pageable pageable = paginatedRequest.generatePageable();
        return eventRepository.findWithChannelByStatus(EventStatus.ACTIVE, pageable).map(UserEventListDTO::of);
    }
}
