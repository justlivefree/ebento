package org.ozbeman.ebento.repository;

import org.ozbeman.ebento.entity.ChannelCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChannelCategoryRepository extends JpaRepository<ChannelCategory, Long> {
    Optional<ChannelCategory> findOneByGuid(UUID guid);
}
