package org.ozbema.ebento.controllers.channel;

import jakarta.validation.Valid;
import org.ozbema.ebento.dto.channel.admin.AdminChannelCreateDTO;
import org.ozbema.ebento.dto.channel.admin.AdminChannelDTO;
import org.ozbema.ebento.dto.channel.admin.AdminChannelListDTO;
import org.ozbema.ebento.dto.channel.admin.AdminUpdateChannelDTO;
import org.ozbema.ebento.dto.event.admin.AdminEventListDTO;
import org.ozbema.ebento.services.channel.AdminChannelManageService;
import org.ozbema.ebento.services.event.AdminEventManageService;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.ozbema.ebento.utils.PaginatedResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/channel")
@PreAuthorize("hasRole('ADMIN')")
public class AdminChannelManageController {

    private final AdminChannelManageService adminChannelManageService;
    private final AdminEventManageService adminEventManageService;

    public AdminChannelManageController(AdminChannelManageService adminChannelManageService, AdminEventManageService adminEventManageService) {
        this.adminChannelManageService = adminChannelManageService;
        this.adminEventManageService = adminEventManageService;
    }

    @GetMapping("")
    public PaginatedResponse<AdminChannelListDTO> getChannels(@Valid PaginatedRequest paginatedRequest) {
        return PaginatedResponse.of(adminChannelManageService.getChannels(paginatedRequest));
    }

    @GetMapping("/{id}")
    public AdminChannelDTO getChannel(@PathVariable UUID id) {
        return adminChannelManageService.getChannel(id);
    }

    @PostMapping("/")
    public AdminChannelDTO createChannel(@Valid @RequestBody AdminChannelCreateDTO adminChannelCreateDTO) {
        return adminChannelManageService.createChannel(adminChannelCreateDTO);
    }

    @PatchMapping("/{id}")
    public AdminChannelDTO updateChannel(
            @PathVariable UUID id,
            @Valid @RequestBody AdminUpdateChannelDTO adminUpdateChannelDTO
    ) {
        return adminChannelManageService.updateChannel(id, adminUpdateChannelDTO);
    }

    @PostMapping(
            value = "/{id}/upload-avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AdminChannelDTO uploadChannelAvatar(
            @PathVariable UUID id,
            @RequestParam MultipartFile file
    ) {
        return adminChannelManageService.uploadChannelAvatar(id, file);
    }

    @PostMapping(
            value = "/{id}/upload-background",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AdminChannelDTO uploadChannelBackground(
            @PathVariable UUID id,
            @RequestParam MultipartFile file
    ) {
        return adminChannelManageService.uploadChannelBackground(id, file);
    }

    @DeleteMapping("/{id}")
    public void deleteChannel(@PathVariable UUID id) {
        adminChannelManageService.deleteChannel(id);
    }


    @GetMapping("/{id}/events")
    public PaginatedResponse<AdminEventListDTO> updateChannel(
            @PathVariable UUID id,
            @Valid PaginatedRequest paginatedRequest
    ) {
        return PaginatedResponse.of(adminEventManageService.getChannelEvents(id, paginatedRequest));
    }

}
