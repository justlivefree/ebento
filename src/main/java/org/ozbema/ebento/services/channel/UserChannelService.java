package org.ozbema.ebento.services.channel;

import org.ozbema.ebento.dto.channel.user.UserChannelDTO;
import org.ozbema.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbema.ebento.dto.event.user.UserEventListDTO;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserChannelService {
    Page<UserChannelListDTO> getChannels(PaginatedRequest paginatedRequest);

    UserChannelDTO getChannel(UUID guid);

    Page<UserEventListDTO> getChannelEvents(UUID channelId, PaginatedRequest paginatedRequest);
}
