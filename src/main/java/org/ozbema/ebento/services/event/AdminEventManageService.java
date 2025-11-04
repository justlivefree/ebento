package org.ozbema.ebento.services.event;

import org.ozbema.ebento.dto.event.admin.AdminEventCreateUpdateDTO;
import org.ozbema.ebento.dto.event.admin.AdminEventListDTO;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AdminEventManageService {
    Page<AdminEventListDTO> getEvents(PaginatedRequest paginatedRequest);

    AdminEventListDTO createEvent(AdminEventCreateUpdateDTO dto);

    AdminEventListDTO updateEvent(UUID guid, AdminEventCreateUpdateDTO dto);

    void deleteEvent(UUID id);

    AdminEventListDTO uploadFileEvent(UUID guid, MultipartFile file);

    Page<AdminEventListDTO> getChannelEvents(UUID id, PaginatedRequest paginatedRequest);
}

