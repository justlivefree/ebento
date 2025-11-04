package org.ozbema.ebento.services.channel.impl;

import org.ozbema.ebento.dto.category.CategoryDTO;
import org.ozbema.ebento.dto.category.CreateUpdateCategoryDTO;
import org.ozbema.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbema.ebento.entity.ChannelCategory;
import org.ozbema.ebento.entity.enums.ChannelStatus;
import org.ozbema.ebento.exceptions.ResourceNotFound;
import org.ozbema.ebento.repository.ChannelCategoryRepository;
import org.ozbema.ebento.repository.ChannelRepository;
import org.ozbema.ebento.services.channel.ChannelCategoryService;
import org.ozbema.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ChannelCategoryServiceImpl implements ChannelCategoryService {
    private final ChannelCategoryRepository channelCategoryRepository;
    private final ChannelRepository channelRepository;

    public ChannelCategoryServiceImpl(ChannelCategoryRepository channelCategoryRepository, ChannelRepository channelRepository) {
        this.channelCategoryRepository = channelCategoryRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public List<CategoryDTO> getCategories() {
        return channelCategoryRepository.findAll().stream().map(CategoryDTO::of).toList();
    }

    @Override
    public CategoryDTO createCategory(CreateUpdateCategoryDTO dto) {
        ChannelCategory category = channelCategoryRepository.save(new ChannelCategory(dto.getTitle()));
        return CategoryDTO.of(category);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(UUID guid, CreateUpdateCategoryDTO dto) {
        ChannelCategory category = channelCategoryRepository.findOneByGuid(guid)
                .orElseThrow(() -> new ResourceNotFound("Category Not Found"));
        category.setTitle(dto.getTitle());
        channelCategoryRepository.save(category);
        return CategoryDTO.of(category);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID guid) {
        ChannelCategory category = channelCategoryRepository.findOneByGuid(guid)
                .orElseThrow(() -> new ResourceNotFound("Category Not Found"));
        channelCategoryRepository.delete(category);
    }

    @Override
    public Page<UserChannelListDTO> getCategoryChannels(UUID guid, PaginatedRequest paginatedRequest) {
        Pageable pageable = paginatedRequest.generatePageable();
        return channelRepository.findByStatusAndCategoryGuid(ChannelStatus.ACTIVE, guid, pageable)
                .map(UserChannelListDTO::of);
    }
}
