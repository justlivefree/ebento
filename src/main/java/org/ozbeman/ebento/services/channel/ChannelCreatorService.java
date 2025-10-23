package org.ozbeman.ebento.services.channel;

import org.ozbeman.ebento.dto.channel.CreatorChannelDTO;
import org.ozbeman.ebento.dto.channel.CreatorUpdateChannelDTO;
import org.ozbeman.ebento.dto.event.CreateEventDTO;
import org.ozbeman.ebento.dto.event.EventDTO;
import org.ozbeman.ebento.dto.event.EventListDTO;
import org.ozbeman.ebento.dto.event.UpdateEventDTO;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ChannelCreatorService {

    CreatorChannelDTO getChannel(Channel channel);

    CreatorChannelDTO updateChannel(Channel channel, CreatorUpdateChannelDTO dto);

    CreatorChannelDTO uploadChannelAvatar(Channel channel, MultipartFile file);

    CreatorChannelDTO uploadChannelBackground(Channel channel, MultipartFile file);

    Page<EventListDTO> getChannelEvents(Channel channel, PaginatedRequest paginatedRequest);

    EventDTO getChannelEvent(Channel channel, UUID eventGuid);

    EventDTO createChannelEvent(Channel channel, CreateEventDTO dto);

    EventDTO updateChannelEvent(Channel channel, UUID eventGuid, UpdateEventDTO dto);

    void deleteChannelEvent(Channel channel, UUID eventGuid);

    EventDTO uploadFileChannelEvent(Channel channel, UUID eventGuid, MultipartFile file);

}
