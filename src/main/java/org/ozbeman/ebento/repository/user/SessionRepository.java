package org.ozbeman.ebento.repository.user;

import org.ozbeman.ebento.entity.Session;
import org.ozbeman.ebento.entity.User;
import org.ozbeman.ebento.entity.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("select distinct s from Session s join fetch s.user u join u.channel where s.guid = :guid")
    Session findOneWithUserByGuid(UUID guid);

    boolean existsSessionByGuid(UUID guid);

    void deleteByGuid(UUID guid);

    Optional<Session> findOneByGuidAndUser(UUID guid, User user);

    List<Session> findByUserAndStatus(User user, SessionStatus status);

    List<Session> findByUserGuid(UUID userGuid);

    void deleteByUser(User user);
}
