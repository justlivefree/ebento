package org.ozbema.ebento.services.channel;

import org.ozbema.ebento.dto.channel.creator.CreatorChannelDTO;
import org.ozbema.ebento.dto.channel.creator.CreatorUpdateChannelDTO;
import org.ozbema.ebento.entity.Channel;
import org.springframework.web.multipart.MultipartFile;

public interface CreatorChannelManageService {

    CreatorChannelDTO getChannel(Channel channel);

    CreatorChannelDTO updateChannel(Channel channel, CreatorUpdateChannelDTO dto);

    CreatorChannelDTO uploadChannelAvatar(Channel channel, MultipartFile file);

    CreatorChannelDTO uploadChannelBackground(Channel channel, MultipartFile file);

}
