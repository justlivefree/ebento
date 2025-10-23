package org.ozbeman.ebento.services.channel.impl;

import org.ozbeman.ebento.dto.channel.CreatorChannelDTO;
import org.ozbeman.ebento.dto.channel.CreatorUpdateChannelDTO;
import org.ozbeman.ebento.dto.event.CreateEventDTO;
import org.ozbeman.ebento.dto.event.EventDTO;
import org.ozbeman.ebento.dto.event.EventListDTO;
import org.ozbeman.ebento.dto.event.UpdateEventDTO;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.Event;
import org.ozbeman.ebento.entity.enums.EventStatus;
import org.ozbeman.ebento.entity.enums.FileType;
import org.ozbeman.ebento.exceptions.InvalidRequestException;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.channel.ChannelRepository;
import org.ozbeman.ebento.repository.event.EventRepository;
import org.ozbeman.ebento.services.channel.ChannelCreatorService;
import org.ozbeman.ebento.utils.ApiUtils;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ChannelCreatorServiceImpl implements ChannelCreatorService {
    private final ChannelRepository channelRepository;
    private final EventRepository eventRepository;

    public ChannelCreatorServiceImpl(ChannelRepository channelRepository, EventRepository eventRepository) {
        this.channelRepository = channelRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CreatorChannelDTO getChannel(Channel channel) {
        if (channel != null) {
            return CreatorChannelDTO.of(channel);
        }
        throw new InvalidRequestException("User has not channel");
    }

    @Override
    @Transactional
    public CreatorChannelDTO updateChannel(Channel channel, CreatorUpdateChannelDTO dto) {
        if (channel == null) {
            throw new InvalidRequestException("User has not channel");
        }
        if (dto.getTitle() != null) {
            channel.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            channel.setDescription(dto.getDescription());
        }
        channelRepository.save(channel);
        return CreatorChannelDTO.of(channel);
    }

    @Override
    @Transactional
    public CreatorChannelDTO uploadChannelAvatar(Channel channel, MultipartFile file) {
        try {
            UUID fileId = UUID.randomUUID();
            FileType fileType = ApiUtils.saveChannelFile(fileId, file);
            channel.setAvatarFileId(fileId);
            channel.setAvatarFileType(fileType);
            channelRepository.save(channel);
            return CreatorChannelDTO.of(channel);
        } catch (IOException e) {
            throw new InvalidRequestException("File is incorrect");
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid file format");
        }
    }

    @Override
    @Transactional
    public CreatorChannelDTO uploadChannelBackground(Channel channel, MultipartFile file) {
        try {
            UUID fileId = UUID.randomUUID();
            FileType fileType = ApiUtils.saveChannelFile(fileId, file);
            channel.setBackgroundFileId(fileId);
            channel.setBackgroundFileType(fileType);
            channelRepository.save(channel);
            return CreatorChannelDTO.of(channel);
        } catch (IOException e) {
            throw new InvalidRequestException("File is incorrect");
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid file format");
        }
    }

    @Override
    public Page<EventListDTO> getChannelEvents(Channel channel, PaginatedRequest paginatedRequest) {
        String search = paginatedRequest.getSearch();
        Pageable pageable = paginatedRequest.generatePageable();
        Page<Event> events;
        if (search != null) {
            events = eventRepository.findByChannelAndTitle(channel, search, pageable);
        } else {
            events = eventRepository.findByChannel(channel, pageable);
        }
        return events.map(EventListDTO::of);
    }

    @Override
    public EventDTO getChannelEvent(Channel channel, UUID eventGuid) {
        return eventRepository.findOneByChannelAndGuid(channel, eventGuid)
                .map(EventDTO::of)
                .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
    }

    @Override
    public EventDTO createChannelEvent(Channel channel, CreateEventDTO dto) {
        Event event = Event.builder()
                .channel(channel)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .hallSize(dto.getHallSize())
                .startDateTime(dto.getStartDateTime())
                .endDate(dto.getEndDate())
                .status(EventStatus.ACTIVE)
                .build();
        eventRepository.save(event);
        return EventDTO.of(event);
    }

    @Override
    public EventDTO updateChannelEvent(Channel channel, UUID eventGuid, UpdateEventDTO dto) {
        Event event = eventRepository.findOneByChannelAndGuid(channel, eventGuid)
                .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getStartDateTime() != null) {
            event.setStartDateTime(dto.getStartDateTime());
        }
        if (dto.getEndDate() != null) {
            event.setEndDate(dto.getEndDate());
        }
        if (dto.getHallSize() != null) {
            event.setHallSize(dto.getHallSize());
        }
        eventRepository.save(event);
        return EventDTO.of(event);
    }

    @Override
    public void deleteChannelEvent(Channel channel, UUID eventGuid) {
        Event event = eventRepository.findOneByChannelAndGuid(channel, eventGuid)
                .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
        eventRepository.delete(event);
    }

    @Override
    public EventDTO uploadFileChannelEvent(Channel channel, UUID eventGuid, MultipartFile file) {
        try {
            Event event = eventRepository.findOneByChannelAndGuid(channel, eventGuid)
                    .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
            UUID fileId = UUID.randomUUID();
            FileType fileType = ApiUtils.saveEventFile(fileId, file);
            event.setFileId(fileId);
            event.setFileType(fileType);
            eventRepository.save(event);
            return EventDTO.of(event);
        } catch (IOException e) {
            throw new InvalidRequestException("File is invalid");
        }

    }
}
