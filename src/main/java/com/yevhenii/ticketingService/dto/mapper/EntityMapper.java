package com.yevhenii.ticketingService.dto.mapper;

import com.yevhenii.ticketingService.entity.BaseEntity;
import com.yevhenii.ticketingService.entity.User;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public User toUser(Long id) {
        return new User(id);
    }



    public Long toIdEntity(BaseEntity entity) {
        return entity.getId();
    }

}
