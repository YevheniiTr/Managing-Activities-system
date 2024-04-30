package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.dto.SaveVenueDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.entity.Venue;

import java.util.List;
import java.util.Optional;


public interface VenueService {
    void save(SaveVenueDTO saveVenueDTO);
    boolean isVenueBelongToUser(Long userId,Long venueId);
    void delete(Long venueId);
    List<VenueDTO> findAllById(Long userId);
    Optional<VenueDTO> findById(Long venueId);
    void update(SaveVenueDTO saveVenueDTO,Long venueId);
    List<VenueDTO> findAllFreeVenuesForCurrentDate();
    List<Venue> findAll();
}
