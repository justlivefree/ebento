package org.ozbema.ebento.services.event.impl;

import org.ozbema.ebento.dto.event.admin.AdminEventCreateUpdateDTO;
import org.ozbema.ebento.dto.event.admin.AdminEventListDTO;
import org.ozbema.ebento.entity.Channel;
import org.ozbema.ebento.entity.Event;
import org.ozbema.ebento.entity.enums.FileType;
import org.ozbema.ebento.exceptions.InvalidRequestException;
import org.ozbema.ebento.exceptions.ResourceNotFound;
import org.ozbema.ebento.repository.ChannelRepository;
import org.ozbema.ebento.repository.EventRepository;
import org.ozbema.ebento.services.event.AdminEventManageService;
import org.ozbema.ebento.utils.FileUtils;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AdminEventManageServiceImpl implements AdminEventManageService {
    private final ChannelRepository channelRepository;
    private final EventRepository eventRepository;
    private final FileUtils fileUtils;

    public AdminEventManageServiceImpl(ChannelRepository channelRepository, EventRepository eventRepository, FileUtils fileUtils) {
        this.channelRepository = channelRepository;
        this.eventRepository = eventRepository;
        this.fileUtils = fileUtils;
    }

    @Override
    public Page<AdminEventListDTO> getEvents(PaginatedRequest paginatedRequest) {
        Pageable pageable = paginatedRequest.generatePageable();
        return eventRepository.findAllWithChannel(pageable).map(AdminEventListDTO::of);
    }

    @Override
    public AdminEventListDTO createEvent(AdminEventCreateUpdateDTO dto) {
        Channel channel = channelRepository.findOneByGuid(dto.getChannelId())
                .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
        Event event = Event.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDateTime(dto.getStartDateTime())
                .endDate(dto.getEndDate())
                .hallSize(dto.getHallSize())
                .lat(dto.getLat())
                .lon(dto.getLon())
                .channel(channel)
                .build();
        eventRepository.save(event);
        return AdminEventListDTO.of(event);
    }

    @Override
    public AdminEventListDTO updateEvent(UUID guid, AdminEventCreateUpdateDTO dto) {
        Event event = eventRepository.findOneByGuid(guid)
                .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
        if (dto.getChannelId() != null) {
            Channel channel = channelRepository.findOneByGuid(dto.getChannelId())
                    .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
            event.setChannel(channel);
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
        if (dto.getLat() != null) {
            event.setLat(dto.getLat());
        }
        if (dto.getLon() != null) {
            event.setLon(dto.getLon());
        }
        eventRepository.save(event);
        return AdminEventListDTO.of(event);
    }

    @Override
    public void deleteEvent(UUID guid) {
        Event event = eventRepository.findOneByGuid(guid)
                .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
        eventRepository.delete(event);
    }

    @Override
    public AdminEventListDTO uploadFileEvent(UUID guid, MultipartFile file) {
        try {
            Event event = eventRepository.findOneByGuid(guid)
                    .orElseThrow(() -> new ResourceNotFound("Event Not Found"));
            UUID fileId = UUID.randomUUID();
            FileType fileType = fileUtils.saveEventFile(fileId, file);
            event.setFileId(fileId);
            event.setFileType(fileType);
            eventRepository.save(event);
            return AdminEventListDTO.of(event);
        } catch (IOException e) {
            throw new InvalidRequestException("File is invalid");
        }
    }

    @Override
    public Page<AdminEventListDTO> getChannelEvents(UUID channelId, PaginatedRequest paginatedRequest) {
        Channel channel = channelRepository.findOneByGuid(channelId)
                .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
        return eventRepository.findByChannel(channel, paginatedRequest.generatePageable())
                .map(AdminEventListDTO::of);
    }
}
