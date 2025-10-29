package org.ozbeman.ebento.controllers.event;

import jakarta.validation.Valid;
import org.ozbeman.ebento.dto.event.admin.AdminEventCreateUpdateDTO;
import org.ozbeman.ebento.dto.event.admin.AdminEventListDTO;
import org.ozbeman.ebento.services.event.AdminEventManageService;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.ozbeman.ebento.utils.PaginatedResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/event")
@PreAuthorize("hasRole('ADMIN')")
public class AdminEventManageController {

    private final AdminEventManageService adminEventManageService;

    public AdminEventManageController(AdminEventManageService adminEventManageService) {
        this.adminEventManageService = adminEventManageService;
    }

    @GetMapping("")
    public PaginatedResponse<AdminEventListDTO> getEvents(@Valid PaginatedRequest paginatedRequest) {
        return PaginatedResponse.of(
                adminEventManageService.getEvents(paginatedRequest)
        );
    }

    @PostMapping("")
    public AdminEventListDTO createEvent(@Valid @RequestBody AdminEventCreateUpdateDTO adminEventCreateDTO) {
        return adminEventManageService.createEvent(adminEventCreateDTO);
    }

    @PatchMapping("/{id}")
    public AdminEventListDTO updateEvent(
            @PathVariable UUID id,
            @Valid @RequestBody AdminEventCreateUpdateDTO adminEventUpdateDTO
    ) {
        return adminEventManageService.updateEvent(id, adminEventUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void updateEvent(@PathVariable UUID id) {
        adminEventManageService.deleteEvent(id);
    }

    @PostMapping(
            value = "/{id}/upload-file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AdminEventListDTO uploadFileEvent(
            @PathVariable UUID id,
            @RequestParam MultipartFile file
    ) {
        return adminEventManageService.uploadFileEvent(id, file);
    }
}
