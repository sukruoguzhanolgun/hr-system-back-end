package com.beam.controller;

import com.beam.controller.dto.JobApplicationDTO;
import com.beam.model.JSONIdModel;
import com.beam.model.Login;
import com.beam.service.JobApplicationService;
import com.beam.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class JobApplicationController {

    private JobApplicationService jobApplicationService;

    private LoginService loginService;

    @Autowired
    public JobApplicationController(JobApplicationService jobApplicationService, LoginService loginService) {
        this.jobApplicationService = jobApplicationService;
        this.loginService = loginService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<JobApplicationDTO> listApplications(){
        return jobApplicationService.listApplications();
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_USER')")
    public HttpStatus addApplication(@RequestBody JobApplicationDTO jobApplicationDTO){
         int id = jobApplicationDTO.getUserDTO().getId();
         Login login = loginService.findUser(id);
         jobApplicationService.saveApplication(jobApplicationDTO,login);
         return HttpStatus.OK;
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpStatus deleteApplication(@RequestBody JSONIdModel jsonIdModel){

        if (jobApplicationService.deleteApplication(jsonIdModel)){
            return HttpStatus.OK;
        }else {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }
    }
    
}
