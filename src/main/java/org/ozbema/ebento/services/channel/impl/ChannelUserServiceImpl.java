package org.ozbema.ebento.services.channel.impl;

import org.ozbema.ebento.dto.channel.user.UserChannelDTO;
import org.ozbema.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbema.ebento.dto.event.user.UserEventListDTO;
import org.ozbema.ebento.entity.Channel;
import org.ozbema.ebento.entity.Event;
import org.ozbema.ebento.entity.enums.ChannelStatus;
import org.ozbema.ebento.entity.enums.EventStatus;
import org.ozbema.ebento.exceptions.ResourceNotFound;
import org.ozbema.ebento.repository.ChannelRepository;
import org.ozbema.ebento.repository.EventRepository;
import org.ozbema.ebento.services.channel.UserChannelService;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChannelUserServiceImpl implements UserChannelService {
    private final ChannelRepository channelRepository;
    private final EventRepository eventRepository;

    public ChannelUserServiceImpl(ChannelRepository channelRepository, EventRepository eventRepository) {
        this.channelRepository = channelRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Page<UserChannelListDTO> getChannels(PaginatedRequest paginatedRequest) {
        Pageable pageable = paginatedRequest.generatePageable();
        return channelRepository.findByStatus(ChannelStatus.ACTIVE, pageable)
                .map(UserChannelListDTO::of);
    }

    @Override
    public UserChannelDTO getChannel(UUID guid) {
        return channelRepository.findOneByGuid(guid)
                .map(UserChannelDTO::of)
                .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
    }

    @Override
    public Page<UserEventListDTO> getChannelEvents(
            UUID channelId,
            PaginatedRequest paginatedRequest
    ) {
        Channel channel = channelRepository.findOneByGuid(channelId)
                .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
        Pageable pageable = paginatedRequest.generatePageable();
        Page<Event> events = eventRepository.findByChannelAndStatusIn(
                channel,
                List.of(EventStatus.ACTIVE, EventStatus.CANCELED),
                pageable
        );
        return events.map(UserEventListDTO::of);
    }
}
