package org.ozbeman.ebento.repository.event;

import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.Event;
import org.ozbeman.ebento.entity.enums.EventStatus;
import org.ozbeman.ebento.entity.enums.FileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByChannel(Channel channel, Pageable pageable);

    Page<Event> findByChannelAndTitle(Channel channel, String title, Pageable pageable);

    Optional<Event> findOneByChannelAndGuid(Channel channel, UUID eventGuid);

    @Query("select e from Event e join fetch e.channel where e.status = :status")
    Page<Event> findWithChannelByStatus(EventStatus status, Pageable pageable);

    Optional<Event> findOneByGuidAndStatus(UUID guid, EventStatus status);

    Page<Event> findByChannelAndStatusIn(Channel channel, List<EventStatus> status, Pageable pageable);

    Optional<Event> findOneByGuid(UUID guid);

    @Query("select e from Event e join fetch e.channel")
    Page<Event> findAllWithChannel(Pageable pageable);

    @Query("select e.fileType from Event e where e.fileId = :fileId")
    Optional<FileType> findOneFileTypeByFileId(UUID fileId);
}
