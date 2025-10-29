package org.ozbeman.ebento.services;

import org.ozbeman.ebento.dto.UserSearchContentResultDTO;
import org.ozbeman.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbeman.ebento.dto.event.user.UserEventListDTO;
import org.ozbeman.ebento.entity.enums.ChannelStatus;
import org.ozbeman.ebento.entity.enums.EventStatus;
import org.ozbeman.ebento.repository.ChannelRepository;
import org.ozbeman.ebento.repository.EventRepository;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.ozbeman.ebento.utils.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserSearchService {
    private final ChannelRepository channelRepository;
    private final EventRepository eventRepository;

    public UserSearchService(ChannelRepository channelRepository, EventRepository eventRepository) {
        this.channelRepository = channelRepository;
        this.eventRepository = eventRepository;
    }

    public PaginatedResponse<UserSearchContentResultDTO> search(String query, PaginatedRequest paginatedRequest) {
        Pageable pageable = paginatedRequest.generatePageable();
        var paginatedResult = new PaginatedResponse<UserSearchContentResultDTO>();
        Page<UserChannelListDTO> channelsPage = channelRepository
                .findByStatusAndTitleContainsIgnoreCase(ChannelStatus.ACTIVE, query, pageable)
                .map(UserChannelListDTO::of);
        Page<UserEventListDTO> eventsPage = eventRepository
                .findByStatusAndTitleContainsIgnoreCase(EventStatus.ACTIVE, query, pageable)
                .map(UserEventListDTO::of);
        paginatedResult.setPageCount(Math.max(eventsPage.getTotalPages(), channelsPage.getTotalPages()));
        paginatedResult.setCurrentPage(pageable.getPageNumber());
        paginatedResult.setHasNext(channelsPage.hasNext() || eventsPage.hasNext());
        paginatedResult.setHasPrev(channelsPage.hasPrevious() || eventsPage.hasPrevious());
        paginatedResult.setSearchData(new UserSearchContentResultDTO(eventsPage.toList(), channelsPage.toList()));
        return paginatedResult;
    }
}
