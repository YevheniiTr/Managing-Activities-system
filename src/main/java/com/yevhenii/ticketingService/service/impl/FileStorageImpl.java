package com.yevhenii.ticketingService.service.impl;

import com.yevhenii.ticketingService.entity.ProveFile;
import com.yevhenii.ticketingService.repository.FileRepository;
import com.yevhenii.ticketingService.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageImpl implements FileStorageService {

    private final FileRepository fileRepository;
    @Autowired
    public FileStorageImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Override
    public ProveFile save(MultipartFile file) throws IOException {
        ProveFile proveFile = new ProveFile();
        proveFile.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        proveFile.setMimeType(file.getContentType());
        proveFile.setData(file.getBytes());
        proveFile.setFileSize(file.getSize());
        fileRepository.save(proveFile);
        return  proveFile;
    }

    @Override
    public Optional<ProveFile> getFile(String id) {
        return fileRepository.findById(id);
    }

    @Override
    public List<ProveFile> getAllFiles() {
        return fileRepository.findAll();
    }
}
