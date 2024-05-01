package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.dto.SaveVenueDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.entity.Venue;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


public interface VenueService {
    void save(SaveVenueDTO saveVenueDTO);
    boolean isVenueBelongToUser(Long userId,Long venueId);
    void delete(Long venueId);
    List<VenueDTO> findAllById(Long userId);
    Optional<VenueDTO> findById(Long venueId);
    void update(SaveVenueDTO saveVenueDTO,Long venueId);
    List<Venue> findAllFreeVenuesForCurrentDate();
    List<Venue> findAllFreeVenuesForDate(Timestamp date);
    List<Venue> findAll();
}
