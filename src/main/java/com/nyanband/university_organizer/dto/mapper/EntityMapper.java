package com.nyanband.university_organizer.dto.mapper;

import com.nyanband.university_organizer.entity.BaseEntity;
import com.nyanband.university_organizer.entity.Profile;
import com.nyanband.university_organizer.entity.Project;
import com.nyanband.university_organizer.entity.Technology;
import com.nyanband.university_organizer.entity.User;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public User toUser(Long id) {
        return new User(id);
    }

    public Profile toProfile(Long id) {
        return new Profile(id);
    }

    public Technology toTechnology(Long technologyId) {
        return new Technology(technologyId);
    }

    public Project toProject(Long projectId) {
        return new Project(projectId);
    }

    public Long toIdEntity(BaseEntity entity) {
        return entity.getId();
    }

}
