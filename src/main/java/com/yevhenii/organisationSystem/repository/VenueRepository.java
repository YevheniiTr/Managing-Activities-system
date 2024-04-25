package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    void deleteById(Long venueId);
    @Query("select count(v) > 0 from Venue v where v.id = :venueId and v.user.id = :userId")
    boolean isVenueBelongToUser(Long userId, Long venueId);
    @Query("select v from Venue v where v.title =:title")
    Venue findByVenueTitle(String title);
    @Query("select v from Venue v where v.title =:title and v.user.id =:userId")
    Venue findByVenueTitleAndUserId(String title,Long userId);


    @Query("select v from  Venue v where v.user.id =:userId")
    List<Venue> findAllById(Long userId);

    Optional<Venue> findById(Long venueId);
}
