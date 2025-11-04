package org.ozbema.ebento.services.channel;

import org.ozbema.ebento.dto.category.CategoryDTO;
import org.ozbema.ebento.dto.category.CreateUpdateCategoryDTO;
import org.ozbema.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbema.ebento.utils.PaginatedRequest;
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
