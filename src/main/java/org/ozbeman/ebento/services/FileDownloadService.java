package org.ozbeman.ebento.services;

import org.ozbeman.ebento.entity.enums.FileType;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.channel.ChannelRepository;
import org.ozbeman.ebento.utils.ApiUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileDownloadService {

    private final ChannelRepository channelRepository;

    public FileDownloadService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Resource downloadChannelAvatarFile(UUID fileId) {
        try {
            FileType fileType = channelRepository.findOneFileTypeByAvatarFileId(fileId)
                    .orElseThrow(() -> new ResourceNotFound("File Not Found"));
            String fileName = "%s.%s".formatted(fileId, fileType.name().toLowerCase());
            File file = ApiUtils.downloadChannelFile(fileName);
            return new InputStreamResource(Files.newInputStream(file.toPath()));
        } catch (IOException e) {
            throw new ResourceNotFound("File Not Found");
        }
    }

    public Resource downloadChannelBackgroundFile(UUID fileId) {
        try {
            FileType fileType = channelRepository.findOneFileTypeByBackgroundFileId(fileId)
                    .orElseThrow(() -> new ResourceNotFound("File Not Found"));
            String fileName = "%s.%s".formatted(fileId, fileType.name().toLowerCase());
            File file = ApiUtils.downloadChannelFile(fileName);
            return new InputStreamResource(Files.newInputStream(file.toPath()));
        } catch (IOException e) {
            throw new ResourceNotFound("File Not Found");
        }
    }
}
