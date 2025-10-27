package org.ozbeman.ebento.services.channel.impl;

import jakarta.validation.Valid;
import org.ozbeman.ebento.dto.channel.admin.AdminChannelDTO;
import org.ozbeman.ebento.dto.channel.admin.AdminChannelListDTO;
import org.ozbeman.ebento.dto.channel.admin.AdminUpdateChannelDTO;
import org.ozbeman.ebento.dto.channel.admin.AdminChannelCreateDTO;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.ChannelCategory;
import org.ozbeman.ebento.entity.Role;
import org.ozbeman.ebento.entity.User;
import org.ozbeman.ebento.entity.enums.ChannelStatus;
import org.ozbeman.ebento.entity.enums.FileType;
import org.ozbeman.ebento.entity.enums.UserRole;
import org.ozbeman.ebento.exceptions.InvalidRequestException;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.channel.ChannelCategoryRepository;
import org.ozbeman.ebento.repository.channel.ChannelRepository;
import org.ozbeman.ebento.repository.event.EventRepository;
import org.ozbeman.ebento.repository.user.RoleRepository;
import org.ozbeman.ebento.repository.user.SessionRepository;
import org.ozbeman.ebento.repository.user.UserRepository;
import org.ozbeman.ebento.services.channel.AdminChannelManageService;
import org.ozbeman.ebento.utils.ApiUtils;
import org.ozbeman.ebento.utils.PaginatedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class AdminChannelManageServiceImpl implements AdminChannelManageService {
    private final ChannelRepository channelRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ChannelCategoryRepository channelCategoryRepository;
    private final SessionRepository sessionRepository;

    public AdminChannelManageServiceImpl(ChannelRepository channelRepository, EventRepository eventRepository, UserRepository userRepository, RoleRepository roleRepository, ChannelCategoryRepository channelCategoryRepository, SessionRepository sessionRepository) {
        this.channelRepository = channelRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.channelCategoryRepository = channelCategoryRepository;
        this.sessionRepository = sessionRepository;
    }

    public Page<AdminChannelListDTO> getChannels(@Valid PaginatedRequest paginatedRequest) {
        Pageable pageable = paginatedRequest.generatePageable();
        String search = paginatedRequest.getSearch();
        if (search != null) {
            return channelRepository.findByTitleContainingIgnoreCase(search, pageable)
                    .map(AdminChannelListDTO::of);
        }
        return channelRepository.findAll(pageable)
                .map(AdminChannelListDTO::of);
    }

    public AdminChannelDTO getChannel(UUID guid) {
        return channelRepository.findOneWithUserByGuid(guid)
                .map(AdminChannelDTO::of)
                .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
    }

    @Transactional
    public AdminChannelDTO createChannel(AdminChannelCreateDTO dto) {
        User user = userRepository.findOneWithChannelByGuid(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFound("User Not Found"));
        Channel channel = user.getChannel();
        if (channel == null) {
            ChannelCategory category = channelCategoryRepository.findOneByGuid(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFound("Category Not Found"));
            sessionRepository.deleteByUser(user);
            roleRepository.save(Role.builder()
                    .role(UserRole.ROLE_CREATOR)
                    .user(user)
                    .build());
            Channel newChannel = channelRepository.save(Channel.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .status(ChannelStatus.ACTIVE)
                    .user(user)
                    .category(category)
                    .build());
            return AdminChannelDTO.of(newChannel);
        } else {
            throw new InvalidRequestException("User already have channel");
        }
    }

    @Transactional
    public AdminChannelDTO updateChannel(UUID guid, AdminUpdateChannelDTO dto) {
        return channelRepository.findOneWithUserByGuid(guid)
                .map(
                        channel -> {
                            if (dto.getDescription() != null) {
                                channel.setDescription(dto.getDescription());
                            }
                            if (dto.getTitle() != null) {
                                channel.setTitle(dto.getTitle());
                            }
                            if (dto.getStatus() != null) {
                                channel.setStatus(dto.getStatus());
                            }
                            if (dto.getCategoryId() != null) {
                                ChannelCategory category = channelCategoryRepository.findOneByGuid(dto.getCategoryId())
                                        .orElseThrow(() -> new ResourceNotFound("Category Not Found"));
                                channel.setCategory(category);
                            }
                            if (dto.getUserId() != null) {
                                channel.setUser(userRepository.findOneWithChannelByGuid(dto.getUserId())
                                        .map(
                                                newUser -> {
                                                    if (newUser.getChannel() == null) {
                                                        User oldUser = channel.getUser();
                                                        roleRepository.deleteByUserAndRole(oldUser, UserRole.ROLE_CREATOR);
                                                        roleRepository.save(Role.builder()
                                                                .role(UserRole.ROLE_CREATOR)
                                                                .user(newUser).build());
                                                        sessionRepository.deleteByUser(newUser);
                                                        sessionRepository.deleteByUser(oldUser);
                                                        return newUser;
                                                    }
                                                    throw new InvalidRequestException("User already have channel");
                                                }
                                        ).orElseThrow(() -> new ResourceNotFound("User Not Found"))
                                );
                            }
                            channelRepository.save(channel);
                            return AdminChannelDTO.of(channel);
                        }
                ).orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
    }

    @Override
    public AdminChannelDTO uploadChannelAvatar(UUID guid, MultipartFile file) {
        try {
            Channel channel = channelRepository.findOneByGuid(guid)
                    .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
            UUID fileId = UUID.randomUUID();
            FileType fileType = ApiUtils.saveChannelFile(fileId, file);
            channel.setAvatarFileId(fileId);
            channel.setAvatarFileType(fileType);
            channelRepository.save(channel);
            return AdminChannelDTO.of(channel);
        } catch (IOException e) {
            throw new InvalidRequestException("File is incorrect");
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid file format");
        }
    }

    @Override
    @Transactional
    public AdminChannelDTO uploadChannelBackground(UUID guid, MultipartFile file) {
        try {
            Channel channel = channelRepository.findOneByGuid(guid)
                    .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
            UUID fileId = UUID.randomUUID();
            FileType fileType = ApiUtils.saveChannelFile(fileId, file);
            channel.setBackgroundFileId(fileId);
            channel.setBackgroundFileType(fileType);
            channelRepository.save(channel);
            return AdminChannelDTO.of(channel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid file format");
        }
    }

    @Transactional
    public void deleteChannel(UUID guid) {
        Channel channel = channelRepository.findOneWithUserByGuid(guid)
                .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
        User user = channel.getUser();
        user.setChannel(null);
        roleRepository.deleteByUserAndRole(user, UserRole.ROLE_CREATOR);
        sessionRepository.deleteByUser(user);
        channelRepository.delete(channel);
    }
}
