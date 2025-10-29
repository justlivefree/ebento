package org.ozbeman.ebento.services.channel.impl;

import org.ozbeman.ebento.dto.channel.user.UserChannelDTO;
import org.ozbeman.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbeman.ebento.dto.event.user.UserEventListDTO;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.Event;
import org.ozbeman.ebento.entity.enums.ChannelStatus;
import org.ozbeman.ebento.entity.enums.EventStatus;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.ChannelRepository;
import org.ozbeman.ebento.repository.EventRepository;
import org.ozbeman.ebento.services.channel.UserChannelService;
import org.ozbeman.ebento.utils.PaginatedRequest;
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
