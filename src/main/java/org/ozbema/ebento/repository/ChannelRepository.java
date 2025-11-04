package org.ozbema.ebento.repository;

import org.ozbema.ebento.entity.Channel;
import org.ozbema.ebento.entity.enums.ChannelStatus;
import org.ozbema.ebento.entity.enums.FileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query("select c from Channel c join fetch c.user where c.guid = :guid")
    Optional<Channel> findOneWithUserByGuid(UUID guid);

    Page<Channel> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Optional<Channel> findOneByGuid(UUID guid);

    @Query("select c.avatarFileType from Channel c where c.avatarFileId = :fileId")
    Optional<FileType> findOneFileTypeByAvatarFileId(UUID fileId);

    @Query("select c.backgroundFileType from Channel c where c.backgroundFileId = :fileId")
    Optional<FileType> findOneFileTypeByBackgroundFileId(UUID fileId);

    Page<Channel> findByStatus(ChannelStatus status, Pageable pageable);

    Page<Channel> findByStatusAndCategoryGuid(ChannelStatus status, UUID categoryGuid, Pageable pageable);

    Page<Channel> findByStatusAndTitleContainsIgnoreCase(ChannelStatus status, String title, Pageable pageable);
}
