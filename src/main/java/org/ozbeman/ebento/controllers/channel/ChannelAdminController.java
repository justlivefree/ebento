package org.ozbeman.ebento.controllers.channel;

import jakarta.validation.Valid;
import org.ozbeman.ebento.dto.channel.AdminChannelDTO;
import org.ozbeman.ebento.dto.channel.AdminUpdateChannelDTO;
import org.ozbeman.ebento.dto.channel.ChannelDTO;
import org.ozbeman.ebento.dto.channel.CreateChannelDTO;
import org.ozbeman.ebento.services.channel.ChannelAdminService;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.ozbeman.ebento.utils.PaginatedResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/channel")
//@PreAuthorize("hasRole('ADMIN')")
public class ChannelAdminController {

    private final ChannelAdminService channelAdminService;

    public ChannelAdminController(ChannelAdminService channelAdminService) {
        this.channelAdminService = channelAdminService;
    }

    @GetMapping("/")
    public PaginatedResponse<ChannelDTO> getChannels(@Valid PaginatedRequest paginatedRequest) {
        return PaginatedResponse.of(channelAdminService.getChannels(paginatedRequest));
    }

    @GetMapping("/{id}")
    public AdminChannelDTO getChannel(@PathVariable UUID id) {
        return channelAdminService.getChannel(id);
    }

    @PostMapping("/")
    public AdminChannelDTO createChannel(@Valid @RequestBody CreateChannelDTO createChannelDTO) {
        return channelAdminService.createChannel(createChannelDTO);
    }

    @PatchMapping("/{id}")
    public AdminChannelDTO updateChannel(
            @PathVariable UUID id,
            @Valid @RequestBody AdminUpdateChannelDTO adminUpdateChannelDTO
    ) {
        return channelAdminService.updateChannel(id, adminUpdateChannelDTO);
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
        return channelAdminService.uploadChannelAvatar(id, file);
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
        return channelAdminService.uploadChannelBackground(id, file);
    }

    @DeleteMapping("/{id}")
    public void deleteChannel(@PathVariable UUID id) {
        channelAdminService.deleteChannel(id);
    }

}
