package org.ozbeman.ebento.services.channel.impl;

import org.ozbeman.ebento.dto.category.CategoryDTO;
import org.ozbeman.ebento.dto.category.CreateUpdateCategoryDTO;
import org.ozbeman.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbeman.ebento.entity.ChannelCategory;
import org.ozbeman.ebento.entity.enums.ChannelStatus;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.channel.ChannelCategoryRepository;
import org.ozbeman.ebento.repository.channel.ChannelRepository;
import org.ozbeman.ebento.services.channel.ChannelCategoryService;
import org.ozbeman.ebento.utils.PaginatedRequest;
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
