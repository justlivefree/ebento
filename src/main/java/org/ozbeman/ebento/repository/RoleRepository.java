package org.ozbeman.ebento.repository;

import org.ozbeman.ebento.entity.Role;
import org.ozbeman.ebento.entity.User;
import org.ozbeman.ebento.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Modifying
    @Transactional
    @Query("delete Role r where r.user = :user and r.role = :role")
    void deleteByUserAndRole(User user, UserRole role);
}
