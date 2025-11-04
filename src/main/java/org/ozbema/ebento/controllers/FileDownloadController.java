package org.ozbema.ebento.controllers;

import org.ozbema.ebento.services.FileDownloadService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/download")
@PreAuthorize("permitAll()")
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    public FileDownloadController(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    @GetMapping("/channel-avatar/{id}")
    public ResponseEntity<Resource> downloadChannelAvatarFile(@PathVariable UUID id) {
        Resource resource = fileDownloadService.downloadChannelAvatarFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/channel-background/{id}")
    public ResponseEntity<Resource> downloadChannelBackgroundFile(@PathVariable UUID id) {
        Resource resource = fileDownloadService.downloadChannelBackgroundFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/event-file/{id}")
    public ResponseEntity<Resource> downloadEventFile(@PathVariable UUID id) {
        Resource resource = fileDownloadService.downloadEventFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
