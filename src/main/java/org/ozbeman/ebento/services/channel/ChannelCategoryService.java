package org.ozbeman.ebento.services.channel;

import jakarta.validation.Valid;
import org.ozbeman.ebento.dto.category.CategoryDTO;
import org.ozbeman.ebento.dto.category.CreateUpdateCategoryDTO;
import org.ozbeman.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ChannelCategoryService {
    List<CategoryDTO> getCategories();

    CategoryDTO createCategory(CreateUpdateCategoryDTO dto);

    CategoryDTO updateCategory(UUID guid, CreateUpdateCategoryDTO dto);

    void deleteCategory(UUID guid);

    Page<UserChannelListDTO> getCategoryChannels(UUID guid, PaginatedRequest paginatedRequest);
}
