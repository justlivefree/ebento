package org.ozbeman.ebento.controllers.channel;

import jakarta.validation.Valid;
import org.ozbeman.ebento.config.CustomUserDetails;
import org.ozbeman.ebento.dto.channel.creator.CreatorChannelDTO;
import org.ozbeman.ebento.dto.channel.creator.CreatorUpdateChannelDTO;
import org.ozbeman.ebento.services.channel.CreatorChannelManageService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/creator/channel")
@PreAuthorize("hasRole('CREATOR')")
public class CreatorChannelManageController {

    private final CreatorChannelManageService creatorChannelManageService;

    public CreatorChannelManageController(CreatorChannelManageService creatorChannelManageService) {
        this.creatorChannelManageService = creatorChannelManageService;
    }

    @GetMapping("")
    public CreatorChannelDTO getChannel(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return creatorChannelManageService.getChannel(userDetails.getUserChannel());
    }

    @PatchMapping("")
    public CreatorChannelDTO updateChannel(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreatorUpdateChannelDTO creatorUpdateChannelDTO
    ) {
        return creatorChannelManageService.updateChannel(userDetails.getUserChannel(), creatorUpdateChannelDTO);
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
        return creatorChannelManageService.uploadChannelAvatar(userDetails.getUserChannel(), file);
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
        return creatorChannelManageService.uploadChannelBackground(userDetails.getUserChannel(), file);
    }
}
