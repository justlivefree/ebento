package org.ozbeman.ebento.services.event.impl;

import org.ozbeman.ebento.dto.event.creator.CreatorCreateUpdateEventDTO;
import org.ozbeman.ebento.dto.event.creator.CreatorEventListDTO;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.Event;
import org.ozbeman.ebento.entity.enums.EventStatus;
import org.ozbeman.ebento.entity.enums.FileType;
import org.ozbeman.ebento.exceptions.InvalidRequestException;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.event.EventRepository;
import org.ozbeman.ebento.services.event.CreatorEventManageService;
import org.ozbeman.ebento.utils.ApiUtils;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class CreatorEventManageServiceImpl implements CreatorEventManageService {
    private final EventRepository eventRepository;

    public CreatorEventManageServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Page<CreatorEventListDTO> getChannelEvents(Channel channel, PaginatedRequest paginatedRequest) {
        String search = paginatedRequest.getSearch();
        Pageable pageable = paginatedRequest.generatePageable();
        Page<Event> events;
        if (search != null) {
            events = eventRepository.findByChannelAndTitle(channel, search, pageable);
        } else {
            events = eventRepository.findByChannel(channel, pageable);
        }
        return events.map(CreatorEventListDTO::of);
    }

    public CreatorEventListDTO getChannelEvent(Channel channel, UUID eventGuid) {
        return eventRepository.findOneByChannelAndGuid(channel, eventGuid)
                .map(CreatorEventListDTO::of)
                .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
    }

    public CreatorEventListDTO createChannelEvent(Channel channel, CreatorCreateUpdateEventDTO dto) {
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
        return CreatorEventListDTO.of(event);
    }

    public CreatorEventListDTO updateChannelEvent(Channel channel, UUID eventGuid, CreatorCreateUpdateEventDTO dto) {
        Event event = eventRepository.findOneByChannelAndGuid(channel, eventGuid)
                .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
        if (event.getStatus() == EventStatus.EXPIRED) {
            throw new InvalidRequestException("Event is expired");
        }
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
        if (dto.getLat() != null){
            event.setLat(dto.getLat());
        }
        if (dto.getLon() != null){
            event.setLon(dto.getLon());
        }
        eventRepository.save(event);
        return CreatorEventListDTO.of(event);
    }

    public void deleteChannelEvent(Channel channel, UUID eventGuid) {
        Event event = eventRepository.findOneByChannelAndGuid(channel, eventGuid)
                .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
        eventRepository.delete(event);
    }

    public CreatorEventListDTO uploadFileChannelEvent(Channel channel, UUID eventGuid, MultipartFile file) {
        try {
            Event event = eventRepository.findOneByChannelAndGuid(channel, eventGuid)
                    .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
            UUID fileId = UUID.randomUUID();
            FileType fileType = ApiUtils.saveEventFile(fileId, file);
            event.setFileId(fileId);
            event.setFileType(fileType);
            eventRepository.save(event);
            return CreatorEventListDTO.of(event);
        } catch (IOException e) {
            throw new InvalidRequestException("File is invalid");
        }

    }
}
