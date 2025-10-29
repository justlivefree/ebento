package org.ozbeman.ebento.utils;


import org.ozbeman.ebento.entity.enums.FileType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileUtils {

    @Value("${ebento.channel.file-path}")
    private String CHANNEL_FILE_PATH;

    @Value("${ebento.event.file-path}")
    private String EVENT_FILE_PATH;

    public String getExtension(String fileName) {
        String[] split = fileName.split("\\.");
        return split[split.length - 1];
    }

    private FileType saveFile(String headPath, UUID fileId, MultipartFile file) throws IOException {
        String fileExtension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        FileType fileType = FileType.valueOf(fileExtension.toUpperCase());
        String fullSavedPath = headPath + File.separator + "%s.%s".formatted(fileId, fileExtension);
        File avatarFile = new File(fullSavedPath);
        Files.copy(file.getInputStream(), avatarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return fileType;
    }

    public FileType saveChannelFile(UUID fileId, MultipartFile file) throws IOException {
        return saveFile(CHANNEL_FILE_PATH, fileId, file);
    }

    public FileType saveEventFile(UUID fileId, MultipartFile file) throws IOException {
        return saveFile(EVENT_FILE_PATH, fileId, file);
    }

    public File downloadChannelFile(String fileName) {
        return new File(CHANNEL_FILE_PATH + File.separator + fileName);
    }

    public File downloadEventFile(String fileName) {
        return new File(EVENT_FILE_PATH + File.separator + fileName);
    }

}
