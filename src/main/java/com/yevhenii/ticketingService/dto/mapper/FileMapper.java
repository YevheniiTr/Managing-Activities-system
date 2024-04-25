package com.yevhenii.ticketingService.dto.mapper;

import com.yevhenii.ticketingService.entity.ProveFile;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {
    public ProveFile toFile(String id) {
        return new ProveFile(id);
    }



    public String toIdEntity(ProveFile proveFile) {
        return proveFile.getId();
    }
}
