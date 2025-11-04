package org.ozbema.ebento.controllers.channel;

import jakarta.validation.Valid;
import org.ozbema.ebento.dto.category.CategoryDTO;
import org.ozbema.ebento.dto.category.CreateUpdateCategoryDTO;
import org.ozbema.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbema.ebento.services.channel.ChannelCategoryService;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.ozbema.ebento.utils.PaginatedResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/channel/category")
public class ChannelCategoryController {

    private final ChannelCategoryService channelCategoryService;

    public ChannelCategoryController(ChannelCategoryService channelCategoryService) {
        this.channelCategoryService = channelCategoryService;
    }

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<CategoryDTO> getCategories() {
        return channelCategoryService.getCategories();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDTO createCategory(@Valid @RequestBody CreateUpdateCategoryDTO createUpdateCategoryDTO) {
        return channelCategoryService.createCategory(createUpdateCategoryDTO);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDTO updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CreateUpdateCategoryDTO createUpdateCategoryDTO
    ) {
        return channelCategoryService.updateCategory(id, createUpdateCategoryDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable UUID id) {
        channelCategoryService.deleteCategory(id);
    }

    @GetMapping("/{id}/channels")
    public PaginatedResponse<UserChannelListDTO> getCategoryChannels(
            @PathVariable UUID id,
            @Valid PaginatedRequest paginatedRequest
    ) {
        return PaginatedResponse.of(
                channelCategoryService.getCategoryChannels(id, paginatedRequest)
        );
    }
}
