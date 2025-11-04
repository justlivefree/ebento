package org.ozbema.ebento.repository;

import org.ozbema.ebento.entity.User;
import org.ozbema.ebento.entity.enums.UserRole;
import org.ozbema.ebento.entity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByPhoneNumber(String phoneNumber);

    Page<User> findByPhoneNumberContainsOrNameContainingIgnoreCase(String phoneNumber, String name, Pageable pageable);

    @Query("select distinct u from User u join fetch u.roles left join fetch u.channel where u.guid = :guid")
    Optional<User> findOneWithRolesAndChannelByGuid(@Param("guid") UUID guid);

    boolean existsUserByPhoneNumber(String phoneNumber);

    @Query("select u from User u left join u.channel where u.guid = :guid")
    Optional<User> findOneWithChannelByGuid(UUID guid);

    @Query("select distinct u from User u join fetch u.roles r where r.role = :role")
    Page<User> findByRole(UserRole role, Pageable pageable);

    Page<User> findByStatus(UserStatus status, Pageable pageable);
}
