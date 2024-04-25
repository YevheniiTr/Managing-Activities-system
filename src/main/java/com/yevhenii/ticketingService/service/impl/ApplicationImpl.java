package com.yevhenii.ticketingService.service.impl;

import com.yevhenii.ticketingService.dto.ApplicationDTO;
import com.yevhenii.ticketingService.dto.SaveApplicationDto;
import com.yevhenii.ticketingService.dto.mapper.ApplicationMapper;
import com.yevhenii.ticketingService.entity.Application;
import com.yevhenii.ticketingService.entity.enums.EApplicationStatus;
import com.yevhenii.ticketingService.repository.ApplicationRepository;
import com.yevhenii.ticketingService.repository.FileRepository;
import com.yevhenii.ticketingService.service.ApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
@Service
public class ApplicationImpl implements ApplicationService {
    ApplicationRepository applicationRepository;
    FileRepository fileRepository;
    ApplicationMapper applicationMapper;


    public ApplicationImpl(ApplicationRepository applicationRepository, FileRepository fileRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.fileRepository = fileRepository;
        this.applicationMapper = applicationMapper;
    }

    @Override
    public void save(SaveApplicationDto saveApplicationDto) {

        Application application =  applicationMapper.toEntity(saveApplicationDto);
        application.setStatus(EApplicationStatus.EXPECT);
        applicationRepository.save(application);
    }

    @Override
    public List<SaveApplicationDto> getApplicationByUserId(long userId) {
        return applicationRepository
                .getApplicationByUserId(userId)
                .stream()
                .map(applicationMapper::toDto)
                .collect(toList());
    }

    @Override
    public List<ApplicationDTO> getApplicationByUserId2(long userId) {
        return applicationRepository
                .getApplicationByUserId(userId)
                .stream()
                .map(applicationMapper::toDto2)
                .collect(toList());
    }

    @Override
    public List<SaveApplicationDto> findAll() {
        return applicationRepository
                .findAll()
                .stream()
                .map(applicationMapper::toDto)
                .collect(toList());
    }

    @Override
    public List<ApplicationDTO> findAll2() {
        return applicationRepository
                .findAll()
                .stream()
                .map(applicationMapper::toDto2)
                .collect(toList());
    }

//    @Override
//    public Optional<SaveApplicationDto> getApplicationByUserId(long userId) {
//        return applicationRepository
//                .getApplicationByUserId(userId)
//                .stream().map(applicationMapper::toDto);
//    }

    @Override
    public Optional<SaveApplicationDto> find(long id) {
        return applicationRepository
                .findById(id)
                .map(applicationMapper::toDto);
    }

    @Override
    public Optional<ApplicationDTO> find2(long id) {
        return applicationRepository
                .findById(id)
                .map(applicationMapper::toDto2);
    }
    @Transactional
    @Override
    public void decline(long id) {

        Application application = applicationRepository.findById2(id);
        application.setStatus(EApplicationStatus.DECLINED);
        applicationRepository.save(application);
    }
    @Transactional
    @Override
    public void approve(long id) {
        Application application = applicationRepository.findById2(id);
        application.setStatus(EApplicationStatus.APPROVED);
        applicationRepository.save(application);

    }
}
