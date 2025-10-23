package org.ozbeman.ebento.services.channel;

import org.ozbeman.ebento.dto.channel.AdminChannelDTO;
import org.ozbeman.ebento.dto.channel.AdminUpdateChannelDTO;
import org.ozbeman.ebento.dto.channel.ChannelDTO;
import org.ozbeman.ebento.dto.channel.CreateChannelDTO;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ChannelAdminService {
    Page<ChannelDTO> getChannels(PaginatedRequest paginatedRequest);

    AdminChannelDTO getChannel(UUID guid);

    AdminChannelDTO createChannel(CreateChannelDTO dto);

    AdminChannelDTO updateChannel(UUID guid, AdminUpdateChannelDTO dto);

    AdminChannelDTO uploadChannelAvatar(UUID guid, MultipartFile file);

    AdminChannelDTO uploadChannelBackground(UUID guid, MultipartFile file);

    void deleteChannel(UUID guid);
}
