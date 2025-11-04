package org.ozbema.ebento.services.channel;

import org.ozbema.ebento.dto.channel.admin.AdminChannelDTO;
import org.ozbema.ebento.dto.channel.admin.AdminChannelListDTO;
import org.ozbema.ebento.dto.channel.admin.AdminUpdateChannelDTO;
import org.ozbema.ebento.dto.channel.admin.AdminChannelCreateDTO;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AdminChannelManageService {
    Page<AdminChannelListDTO> getChannels(PaginatedRequest paginatedRequest);

    AdminChannelDTO getChannel(UUID guid);

    AdminChannelDTO createChannel(AdminChannelCreateDTO dto);

    AdminChannelDTO updateChannel(UUID guid, AdminUpdateChannelDTO dto);

    AdminChannelDTO uploadChannelAvatar(UUID guid, MultipartFile file);

    AdminChannelDTO uploadChannelBackground(UUID guid, MultipartFile file);

    void deleteChannel(UUID guid);
}
