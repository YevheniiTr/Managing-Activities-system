package com.yevhenii.ticketingService.service;

import com.yevhenii.ticketingService.entity.ProveFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileStorageService {

    public ProveFile save(MultipartFile file) throws  IOException;
    public Optional getFile(String   id);
    public List<ProveFile> getAllFiles();

}
