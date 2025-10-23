package org.ozbeman.ebento.repository.event;

import org.ozbeman.ebento.dto.event.EventListDTO;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.Event;
import org.ozbeman.ebento.entity.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Integer countByChannelGuidAndStatus(UUID channelGuid, EventStatus status);

    Page<Event> findByChannel(Channel channel, Pageable pageable);

    Page<Event> findByChannelAndTitle(Channel channel, String title, Pageable pageable);

    Optional<Event> findOneByChannelAndGuid(Channel channel, UUID eventGuid);
}
