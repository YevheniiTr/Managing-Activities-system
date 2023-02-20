package com.nyanband.university_organizer.dto.mapper;

import com.nyanband.university_organizer.entity.BaseEntity;
import com.nyanband.university_organizer.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BaseEntityIdMapper {

    public User toUser(Long id) {
        return new User(id);
    }

    public Long toIdEntity(BaseEntity entity){
        return entity.getId();
    }

}
