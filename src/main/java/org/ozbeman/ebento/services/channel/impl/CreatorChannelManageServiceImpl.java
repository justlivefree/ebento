package org.ozbeman.ebento.services.channel.impl;

import org.ozbeman.ebento.dto.channel.creator.CreatorChannelDTO;
import org.ozbeman.ebento.dto.channel.creator.CreatorUpdateChannelDTO;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.enums.FileType;
import org.ozbeman.ebento.exceptions.InvalidRequestException;
import org.ozbeman.ebento.repository.ChannelRepository;
import org.ozbeman.ebento.services.channel.CreatorChannelManageService;
import org.ozbeman.ebento.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class CreatorChannelManageServiceImpl implements CreatorChannelManageService {
    private final ChannelRepository channelRepository;
    private final FileUtils fileUtils;

    public CreatorChannelManageServiceImpl(ChannelRepository channelRepository, FileUtils fileUtils) {
        this.channelRepository = channelRepository;
        this.fileUtils = fileUtils;
    }

    @Override
    public CreatorChannelDTO getChannel(Channel channel) {
        if (channel != null) {
            return CreatorChannelDTO.of(channel);
        }
        throw new InvalidRequestException("User has not channel");
    }

    @Override
    @Transactional
    public CreatorChannelDTO updateChannel(Channel channel, CreatorUpdateChannelDTO dto) {
        if (channel == null) {
            throw new InvalidRequestException("User has not channel");
        }
        if (dto.getTitle() != null) {
            channel.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            channel.setDescription(dto.getDescription());
        }
        channelRepository.save(channel);
        return CreatorChannelDTO.of(channel);
    }

    @Override
    @Transactional
    public CreatorChannelDTO uploadChannelAvatar(Channel channel, MultipartFile file) {
        try {
            UUID fileId = UUID.randomUUID();
            FileType fileType = fileUtils.saveChannelFile(fileId, file);
            channel.setAvatarFileId(fileId);
            channel.setAvatarFileType(fileType);
            channelRepository.save(channel);
            return CreatorChannelDTO.of(channel);
        } catch (IOException e) {
            throw new InvalidRequestException("File is incorrect");
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid file format");
        }
    }

    @Override
    @Transactional
    public CreatorChannelDTO uploadChannelBackground(Channel channel, MultipartFile file) {
        try {
            UUID fileId = UUID.randomUUID();
            FileType fileType = fileUtils.saveChannelFile(fileId, file);
            channel.setBackgroundFileId(fileId);
            channel.setBackgroundFileType(fileType);
            channelRepository.save(channel);
            return CreatorChannelDTO.of(channel);
        } catch (IOException e) {
            throw new InvalidRequestException("File is incorrect");
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid file format");
        }
    }
}
