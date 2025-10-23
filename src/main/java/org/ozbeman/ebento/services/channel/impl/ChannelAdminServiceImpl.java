package org.ozbeman.ebento.services.channel.impl;

import jakarta.validation.Valid;
import org.ozbeman.ebento.dto.channel.AdminChannelDTO;
import org.ozbeman.ebento.dto.channel.AdminUpdateChannelDTO;
import org.ozbeman.ebento.dto.channel.ChannelDTO;
import org.ozbeman.ebento.dto.channel.CreateChannelDTO;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.Role;
import org.ozbeman.ebento.entity.User;
import org.ozbeman.ebento.entity.enums.ChannelStatus;
import org.ozbeman.ebento.entity.enums.EventStatus;
import org.ozbeman.ebento.entity.enums.FileType;
import org.ozbeman.ebento.entity.enums.UserRole;
import org.ozbeman.ebento.exceptions.InvalidRequestException;
import org.ozbeman.ebento.exceptions.ResourceNotFound;
import org.ozbeman.ebento.repository.channel.ChannelRepository;
import org.ozbeman.ebento.repository.event.EventRepository;
import org.ozbeman.ebento.repository.user.RoleRepository;
import org.ozbeman.ebento.repository.user.UserRepository;
import org.ozbeman.ebento.services.channel.ChannelAdminService;
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
public class ChannelAdminServiceImpl implements ChannelAdminService {
    private final ChannelRepository channelRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ChannelAdminServiceImpl(ChannelRepository channelRepository, EventRepository eventRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.channelRepository = channelRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Page<ChannelDTO> getChannels(@Valid PaginatedRequest paginatedRequest) {
        Pageable pageable = paginatedRequest.generatePageable();
        String search = paginatedRequest.getSearch();
        if (search != null) {
            return channelRepository.findByTitleContainingIgnoreCase(search, pageable)
                    .map(ChannelDTO::of);
        }
        return channelRepository.findAll(pageable)
                .map(ChannelDTO::of);
    }

    public AdminChannelDTO getChannel(UUID guid) {
        return channelRepository.findOneWithUserByGuid(guid)
                .map(
                        channel -> {
                            AdminChannelDTO dto = AdminChannelDTO.of(channel);
                            dto.setActiveEventsCount(eventRepository.countByChannelGuidAndStatus(guid, EventStatus.ACTIVE));
                            dto.setExpiredEventsCount(eventRepository.countByChannelGuidAndStatus(guid, EventStatus.EXPIRED));
                            dto.setCanceledEventsCount(eventRepository.countByChannelGuidAndStatus(guid, EventStatus.CANCELED));
                            return dto;
                        }
                )
                .orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
    }

    @Transactional
    public AdminChannelDTO createChannel(CreateChannelDTO dto) {
        User user = userRepository.findOneWithChannelByGuid(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFound("User Not Found"));
        Channel channel = user.getChannel();
        if (channel == null) {
            roleRepository.save(Role.builder()
                    .role(UserRole.ROLE_CREATOR)
                    .user(user)
                    .build());
            Channel newChannel = channelRepository.save(Channel.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .status(ChannelStatus.ACTIVE)
                    .user(user)
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
                            if (dto.getUserId() != null) {
                                userRepository.findOneWithChannelByGuid(dto.getUserId())
                                        .map(
                                                user -> {
                                                    if (user.getChannel() == null) {
                                                        roleRepository.deleteByUserAndRole(channel.getUser(), UserRole.ROLE_CREATOR);
                                                        roleRepository.save(Role.builder()
                                                                .role(UserRole.ROLE_CREATOR)
                                                                .user(user).build());
                                                        channel.setUser(user);
                                                        return user;
                                                    }
                                                    throw new InvalidRequestException("User already have channel");
                                                }
                                        ).orElseThrow(() -> new ResourceNotFound("User Not Found"));
                            }
                            channelRepository.save(channel);
                            return AdminChannelDTO.of(channel);
                        }
                ).orElseThrow(() -> new ResourceNotFound("Channel Not Found"));
    }

    @Override
    public AdminChannelDTO uploadChannelAvatar(UUID guid, MultipartFile file) {
        try {
            Channel channel = channelRepository.findByGuid(guid)
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
            Channel channel = channelRepository.findByGuid(guid)
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
        channelRepository.delete(channel);
    }
}
