package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.entity.Edge;

import java.util.Optional;

public interface EdgeService {
    Optional<Edge> findById(Long id);
}
