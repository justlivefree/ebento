package org.ozbema.ebento.services.event;

import org.ozbema.ebento.dto.event.creator.CreatorCreateUpdateEventDTO;
import org.ozbema.ebento.dto.event.creator.CreatorEventListDTO;
import org.ozbema.ebento.entity.Channel;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CreatorEventManageService {

    Page<CreatorEventListDTO> getChannelEvents(Channel channel, PaginatedRequest paginatedRequest);

    CreatorEventListDTO getChannelEvent(Channel channel, UUID eventGuid);

    CreatorEventListDTO createChannelEvent(Channel channel, CreatorCreateUpdateEventDTO dto);

    CreatorEventListDTO updateChannelEvent(Channel channel, UUID eventGuid, CreatorCreateUpdateEventDTO dto);

    void deleteChannelEvent(Channel channel, UUID eventGuid);

    CreatorEventListDTO uploadFileChannelEvent(Channel channel, UUID eventGuid, MultipartFile file);
}
