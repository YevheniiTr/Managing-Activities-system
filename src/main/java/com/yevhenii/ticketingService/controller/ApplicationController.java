package com.yevhenii.ticketingService.controller;

import com.yevhenii.ticketingService.controller.util.SecurityUtils;
import com.yevhenii.ticketingService.dto.ApplicationDTO;
import com.yevhenii.ticketingService.dto.FileResponse;
import com.yevhenii.ticketingService.dto.SaveApplicationDto;
import com.yevhenii.ticketingService.entity.ProveFile;
import com.yevhenii.ticketingService.entity.enums.EApplicationStatus;
import com.yevhenii.ticketingService.exception.EntityNotFoundException;
import com.yevhenii.ticketingService.service.ApplicationService;
import com.yevhenii.ticketingService.service.FileStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApplicationController {
    FileStorageService fileStorageService;
    SecurityUtils securityUtils;

    ApplicationService applicationService;

    public ApplicationController(FileStorageService fileStorageService, SecurityUtils securityUtils, ApplicationService applicationService) {
        this.fileStorageService = fileStorageService;
        this.securityUtils = securityUtils;
        this.applicationService = applicationService;
    }

    @GetMapping({"application/{applicationId}"})
    public String getApplication(@PathVariable Long applicationId,
                                 Model model) {
        ApplicationDTO application = applicationService.find2(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Project with id " + applicationId + " does not exist"));
        model.addAttribute("application2", application);
        System.out.println(application.getFileId());

        Optional<ProveFile> fileEntityOptional = fileStorageService.getFile(application.getFileId());
        ProveFile fileEntity = fileEntityOptional.get();
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileEntity.getId())
                .toUriString();
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(fileEntity.getId());
        fileResponse.setName(fileEntity.getName());
        fileResponse.setContentType(fileEntity.getMimeType());
        fileResponse.setSize(fileEntity.getFileSize());
        fileResponse.setUrl(downloadURL);
        model.addAttribute("fileResponse", fileResponse);
        if (!securityUtils.checkAdmin()) {
            return "applicationDetails";
        } else
            return "admin_appDetails";
    }


    @PostMapping("/upload")
    public RedirectView uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileStorageService.save(file);
            System.out.println("File uploaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new RedirectView("my_application");
    }

    @Transactional
    @GetMapping({"/application"})
    public String application(Model model) {
        long userId = securityUtils.getUserId();
        model.addAttribute("userApplications", applicationService.getApplicationByUserId2(securityUtils.getUserId()));
        return "my_application";
    }

    @GetMapping("/sent_appl")
    public String appl() {
        return "sent_application";
    }


    @Transactional
    @GetMapping("/admin/applications")
    public String getAllApplications(Model model) {
        if (!securityUtils.checkAdmin()) {
            return "not_admin_error";
        } else {
            List<SaveApplicationDto> list = applicationService.findAll();
            for (SaveApplicationDto saveApplicationDto : list) {
                System.out.println(saveApplicationDto.getTitle());
            }
            model.addAttribute("applications", applicationService.findAll2());
            return "organisator_applications";
        }
    }





    @PostMapping({"/admin/decline/{id}"})
    public RedirectView decline(@PathVariable Long id) {

        applicationService.decline(id);

        return new RedirectView("/admin/applications");
    }
    @PostMapping({"/admin/approve/{id}"})
    public RedirectView approve(@PathVariable Long id) {

        applicationService.approve(id);

        return new RedirectView("/admin/applications");
    }


    @Transactional
    @PostMapping("/application/create")
    public RedirectView createApplication(
            @RequestParam String orgName,
            @RequestParam String orgPhone,
            @RequestParam String city,
            @RequestParam String location,
            @RequestParam String title,
            @RequestParam String startDate,
            @RequestParam("file") MultipartFile file
    ) {
        ProveFile proveFile;
        try {
            proveFile = fileStorageService.save(file);
            System.out.println("File uploaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Парсинг рядка з датою у об'єкт типу Date
        Date date;
        try {
            date = dateFormat.parse(startDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(proveFile.getId());
        SaveApplicationDto saveApplicationDto = new SaveApplicationDto(
                orgName,
                orgPhone,
                city,
                location,
                title,
                date,
                EApplicationStatus.EXPECT,
                securityUtils.getUserId(),
                proveFile.getId()
        );
        System.out.println(saveApplicationDto.getFileId());
        applicationService.save(saveApplicationDto);

        return new RedirectView("/application");
    }
}
