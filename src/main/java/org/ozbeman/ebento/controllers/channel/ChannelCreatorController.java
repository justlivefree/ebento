package org.ozbeman.ebento.controllers.channel;

import jakarta.validation.Valid;
import org.ozbeman.ebento.config.CustomUserDetails;
import org.ozbeman.ebento.dto.channel.CreatorChannelDTO;
import org.ozbeman.ebento.dto.channel.CreatorUpdateChannelDTO;
import org.ozbeman.ebento.dto.event.CreateEventDTO;
import org.ozbeman.ebento.dto.event.EventDTO;
import org.ozbeman.ebento.dto.event.EventListDTO;
import org.ozbeman.ebento.dto.event.UpdateEventDTO;
import org.ozbeman.ebento.services.channel.ChannelCreatorService;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.ozbeman.ebento.utils.PaginatedResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/creator/channel")
@PreAuthorize("hasRole('CREATOR')")
public class ChannelCreatorController {

    private final ChannelCreatorService channelCreatorService;

    public ChannelCreatorController(ChannelCreatorService channelCreatorService) {
        this.channelCreatorService = channelCreatorService;
    }

    @GetMapping("")
    public CreatorChannelDTO getChannel(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return channelCreatorService.getChannel(userDetails.getUserChannel());
    }

    @PatchMapping("")
    public CreatorChannelDTO updateChannel(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreatorUpdateChannelDTO creatorUpdateChannelDTO
    ) {
        return channelCreatorService.updateChannel(userDetails.getUserChannel(), creatorUpdateChannelDTO);
    }

    @PostMapping(
            value = "/upload-avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CreatorChannelDTO uploadChannelAvatar(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam MultipartFile file
    ) {
        return channelCreatorService.uploadChannelAvatar(userDetails.getUserChannel(), file);
    }

    @PostMapping(
            value = "/upload-background",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CreatorChannelDTO uploadChannelBackground(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam MultipartFile file
    ) {
        return channelCreatorService.uploadChannelBackground(userDetails.getUserChannel(), file);
    }

    @GetMapping("/events")
    public PaginatedResponse<EventListDTO> getChannelEvents(
            @Valid PaginatedRequest paginatedRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return PaginatedResponse.of(
                channelCreatorService.getChannelEvents(userDetails.getUserChannel(), paginatedRequest)
        );
    }

    @GetMapping("/events/{id}")
    public EventDTO getChannelEvent(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return channelCreatorService.getChannelEvent(userDetails.getUserChannel(), id);
    }

    @PostMapping("/events")
    public EventDTO createChannelEvent(
            @Valid @RequestBody CreateEventDTO createEventDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return channelCreatorService.createChannelEvent(userDetails.getUserChannel(), createEventDTO);
    }

    @PatchMapping("/events/{id}")
    public EventDTO updateChannelEvent(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEventDTO updateEventDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return channelCreatorService.updateChannelEvent(userDetails.getUserChannel(), id, updateEventDTO);
    }

    @DeleteMapping("/events/{id}")
    public void deleteChannelEvent(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        channelCreatorService.deleteChannelEvent(userDetails.getUserChannel(), id);
    }

    @PostMapping(
            value = "/events/{id}/upload-file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public EventDTO uploadFileChannelEvent(
            @PathVariable UUID id,
            @RequestParam MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return channelCreatorService.uploadFileChannelEvent(userDetails.getUserChannel(), id, file);
    }
}
