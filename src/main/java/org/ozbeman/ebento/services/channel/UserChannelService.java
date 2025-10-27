package org.ozbeman.ebento.services.channel;

import org.ozbeman.ebento.config.CustomUserDetails;
import org.ozbeman.ebento.dto.channel.user.UserChannelDTO;
import org.ozbeman.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbeman.ebento.dto.event.user.UserEventListDTO;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserChannelService {
    Page<UserChannelListDTO> getChannels(PaginatedRequest paginatedRequest);

    UserChannelDTO getChannel(UUID guid, CustomUserDetails userDetails);

    Page<UserEventListDTO> getChannelEvents(UUID channelId, PaginatedRequest paginatedRequest);
}
