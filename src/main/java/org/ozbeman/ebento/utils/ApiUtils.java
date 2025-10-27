package org.ozbeman.ebento.utils;


import org.ozbeman.ebento.entity.enums.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;


public class ApiUtils {
    public static final String CHANNEL_FILE_PATH = "/home/user/Projects/IdeaProjects/ebento/media/channel";
    public static final String EVENT_FILE_PATH = "/home/user/Projects/IdeaProjects/ebento/media/event";


    public static String getExtension(String fileName) {
        String[] split = fileName.split("\\.");
        return split[split.length - 1];
    }

    public static FileType saveChannelFile(UUID fileId, MultipartFile file) throws IOException {
        String fileExtension = ApiUtils.getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        FileType fileType = FileType.valueOf(fileExtension.toUpperCase());
        String fullSavedPath = CHANNEL_FILE_PATH + File.separator + "%s.%s".formatted(fileId, fileExtension);
        File avatarFile = new File(fullSavedPath);
        Files.copy(file.getInputStream(), avatarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return fileType;
    }

    public static FileType saveEventFile(UUID fileId, MultipartFile file) throws IOException {
        String fileExtension = ApiUtils.getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        FileType fileType = FileType.valueOf(fileExtension.toUpperCase());
        String fullSavedPath = EVENT_FILE_PATH + File.separator + "%s.%s".formatted(fileId, fileExtension);
        File avatarFile = new File(fullSavedPath);
        Files.copy(file.getInputStream(), avatarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return fileType;
    }

    public static File downloadChannelFile(String fileName) {
        return new File(CHANNEL_FILE_PATH + File.separator + fileName);
    }

    public static File downloadEventFile(String fileName) {
        return new File(EVENT_FILE_PATH + File.separator + fileName);
    }

}
