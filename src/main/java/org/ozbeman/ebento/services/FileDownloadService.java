package org.ozbeman.ebento.services;

import org.ozbeman.ebento.entity.enums.FileType;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.ChannelRepository;
import org.ozbeman.ebento.repository.EventRepository;
import org.ozbeman.ebento.utils.FileUtils;
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
    private final EventRepository eventRepository;
    private final FileUtils fileUtils;

    public FileDownloadService(ChannelRepository channelRepository, EventRepository eventRepository, FileUtils fileUtils) {
        this.channelRepository = channelRepository;
        this.eventRepository = eventRepository;
        this.fileUtils = fileUtils;
    }

    public Resource downloadChannelAvatarFile(UUID fileId) {
        try {
            FileType fileType = channelRepository.findOneFileTypeByAvatarFileId(fileId)
                    .orElseThrow(() -> new ResourceNotFound("File Not Found"));
            String fileName = "%s.%s".formatted(fileId, fileType.name().toLowerCase());
            File file = fileUtils.downloadChannelFile(fileName);
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
            File file = fileUtils.downloadChannelFile(fileName);
            return new InputStreamResource(Files.newInputStream(file.toPath()));
        } catch (IOException e) {
            throw new ResourceNotFound("File Not Found");
        }
    }

    public Resource downloadEventFile(UUID fileId) {
        try {
            FileType fileType = eventRepository.findOneFileTypeByFileId(fileId)
                    .orElseThrow(() -> new ResourceNotFound("File Not Found"));
            String fileName = "%s.%s".formatted(fileId, fileType.name().toLowerCase());
            File file = fileUtils.downloadEventFile(fileName);
            return new InputStreamResource(Files.newInputStream(file.toPath()));
        } catch (IOException e) {
            throw new ResourceNotFound("File Not Found");
        }
    }
}
