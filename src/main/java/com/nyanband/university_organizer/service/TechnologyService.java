package com.nyanband.university_organizer.service;

import com.nyanband.university_organizer.dto.ViewTechnologyDTO;

import java.util.List;

public interface TechnologyService {

    List<ViewTechnologyDTO> findAll();

}
