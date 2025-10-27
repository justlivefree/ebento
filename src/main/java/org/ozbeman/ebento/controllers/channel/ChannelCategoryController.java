package org.ozbeman.ebento.controllers.channel;

import jakarta.validation.Valid;
import org.ozbeman.ebento.dto.category.CategoryDTO;
import org.ozbeman.ebento.dto.category.CreateUpdateCategoryDTO;
import org.ozbeman.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbeman.ebento.services.channel.ChannelCategoryService;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.ozbeman.ebento.utils.PaginatedResponse;
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
    public List<CategoryDTO> getCategories() {
        return channelCategoryService.getCategories();
    }

    @PostMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDTO createCategory(@Valid @RequestBody CreateUpdateCategoryDTO createUpdateCategoryDTO) {
        return channelCategoryService.createCategory(createUpdateCategoryDTO);
    }

    @PatchMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDTO updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CreateUpdateCategoryDTO createUpdateCategoryDTO
    ) {
        return channelCategoryService.updateCategory(id, createUpdateCategoryDTO);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
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
