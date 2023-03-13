package com.nyanband.university_organizer.repository;

import com.nyanband.university_organizer.entity.Role;
import com.nyanband.university_organizer.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}