package org.ozbema.ebento.controllers.event;

import jakarta.validation.Valid;
import org.ozbema.ebento.config.CustomUserDetails;
import org.ozbema.ebento.dto.event.creator.CreatorCreateUpdateEventDTO;
import org.ozbema.ebento.dto.event.creator.CreatorEventListDTO;
import org.ozbema.ebento.services.event.CreatorEventManageService;
import org.ozbema.ebento.services.event.impl.CreatorEventManageServiceImpl;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.ozbema.ebento.utils.PaginatedResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/creator/event")
@PreAuthorize("hasRole('CREATOR')")
public class CreatorEventManageController {
    private final CreatorEventManageService creatorEventManageService;

    public CreatorEventManageController(CreatorEventManageServiceImpl creatorEventManageService) {
        this.creatorEventManageService = creatorEventManageService;
    }

    @GetMapping("")
    public PaginatedResponse<CreatorEventListDTO> getChannelEvents(
            @Valid PaginatedRequest paginatedRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return PaginatedResponse.of(
                creatorEventManageService.getChannelEvents(userDetails.getUserChannel(), paginatedRequest)
        );
    }

    @GetMapping("/{id}")
    public CreatorEventListDTO getEvent(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return creatorEventManageService.getChannelEvent(userDetails.getUserChannel(), id);
    }

    @PostMapping("")
    public CreatorEventListDTO createEvent(
            @Valid @RequestBody CreatorCreateUpdateEventDTO createEventDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return creatorEventManageService.createChannelEvent(userDetails.getUserChannel(), createEventDTO);
    }

    @PatchMapping("/{id}")
    public CreatorEventListDTO updateEvent(
            @PathVariable UUID id,
            @Valid @RequestBody CreatorCreateUpdateEventDTO updateEventDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return creatorEventManageService.updateChannelEvent(userDetails.getUserChannel(), id, updateEventDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        creatorEventManageService.deleteChannelEvent(userDetails.getUserChannel(), id);
    }

    @PostMapping(
            value = "/{id}/upload-file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CreatorEventListDTO uploadFileEvent(
            @PathVariable UUID id,
            @RequestParam MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return creatorEventManageService.uploadFileChannelEvent(userDetails.getUserChannel(), id, file);
    }
}
