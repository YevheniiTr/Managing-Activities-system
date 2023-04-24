package com.nyanband.university_organizer.service.impl;

import com.nyanband.university_organizer.dto.ViewTechnologyDTO;
import com.nyanband.university_organizer.dto.mapper.TechnologyMapper;
import com.nyanband.university_organizer.repository.TechnologyRepository;
import com.nyanband.university_organizer.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnologyServiceImpl implements TechnologyService {

    TechnologyRepository technologyRepository;
    TechnologyMapper technologyMapper;

    @Autowired
    public TechnologyServiceImpl(TechnologyRepository technologyRepository,
                                 TechnologyMapper technologyMapper) {
        this.technologyRepository = technologyRepository;
        this.technologyMapper = technologyMapper;
    }

    @Override
    public List<ViewTechnologyDTO> findAll() {
        return technologyRepository.findAll().stream()
                .map(technologyMapper::toDto)
                .collect(Collectors.toList());
    }
}
