package org.ozbeman.ebento.controllers.channel;

import jakarta.validation.Valid;
import org.ozbeman.ebento.dto.channel.user.UserChannelDTO;
import org.ozbeman.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbeman.ebento.dto.event.user.UserEventListDTO;
import org.ozbeman.ebento.services.channel.UserChannelService;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.ozbeman.ebento.utils.PaginatedResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/channel")
@PreAuthorize("permitAll()")
public class UserChannelController {
    private final UserChannelService userChannelService;

    public UserChannelController(UserChannelService userChannelService) {
        this.userChannelService = userChannelService;
    }

    @GetMapping("")
    public PaginatedResponse<UserChannelListDTO> getChannels(@Valid PaginatedRequest paginatedRequest) {
        return PaginatedResponse.of(userChannelService.getChannels(paginatedRequest));
    }

    @GetMapping("/{id}")
    public UserChannelDTO getChannel(@PathVariable UUID id) {
        return userChannelService.getChannel(id);
    }

    @GetMapping("/{id}/events")
    public PaginatedResponse<UserEventListDTO> getChannelEvents(
            @PathVariable UUID id,
            @Valid PaginatedRequest paginatedRequest
    ) {
        return PaginatedResponse.of(
                userChannelService.getChannelEvents(id, paginatedRequest)
        );
    }

}
