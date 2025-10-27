package org.ozbeman.ebento.services.channel;

import org.ozbeman.ebento.dto.channel.creator.CreatorChannelDTO;
import org.ozbeman.ebento.dto.channel.creator.CreatorUpdateChannelDTO;
import org.ozbeman.ebento.entity.Channel;
import org.springframework.web.multipart.MultipartFile;

public interface CreatorChannelManageService {

    CreatorChannelDTO getChannel(Channel channel);

    CreatorChannelDTO updateChannel(Channel channel, CreatorUpdateChannelDTO dto);

    CreatorChannelDTO uploadChannelAvatar(Channel channel, MultipartFile file);

    CreatorChannelDTO uploadChannelBackground(Channel channel, MultipartFile file);

}
