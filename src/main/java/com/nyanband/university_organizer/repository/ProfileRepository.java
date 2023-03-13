package com.nyanband.university_organizer.repository;

import com.nyanband.university_organizer.entity.Profile;
import com.nyanband.university_organizer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(long userId);

}
