package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.repository.EdgeRepository;
import com.yevhenii.organisationSystem.services.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EdgeServiceImpl implements EdgeService {

    EdgeRepository edgeRepository;

    @Autowired
    public EdgeServiceImpl(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }

    @Override
    public Optional<Edge> findById(Long id) {
        return edgeRepository.findById(id);
    }
}
