package org.ozbema.ebento.controllers;

import jakarta.validation.Valid;
import org.ozbema.ebento.dto.UserSearchContentResultDTO;
import org.ozbema.ebento.services.UserSearchService;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.ozbema.ebento.utils.PaginatedResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
@PreAuthorize("permitAll()")
public class UserSearchContentController {
    private final UserSearchService userSearchService;

    public UserSearchContentController(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @GetMapping("")
    public PaginatedResponse<UserSearchContentResultDTO> search(
            @RequestParam String q,
            @Valid PaginatedRequest paginatedRequest
    ) {
        return userSearchService.search(q, paginatedRequest);
    }
}
