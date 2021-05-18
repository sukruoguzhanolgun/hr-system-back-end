package com.beam.service;

import com.beam.controller.dto.JobApplicationDTO;
import com.beam.model.JSONIdModel;
import com.beam.model.JobApplication;
import com.beam.model.Login;
import com.beam.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;


    @Autowired
    public JobApplicationService(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;

    }

    public List<JobApplicationDTO> listApplications(){
        List<JobApplication> applicationList = jobApplicationRepository.findAll();
        return applicationList.stream()
                .map(JobApplicationService::mapToDTO)
                .collect(Collectors.toList());
    }

    public void saveApplication(JobApplicationDTO jobApplicationDTO,Login login){
        int id = 0;
        mapToDTO(this.jobApplicationRepository.save(
                new JobApplication(
                        id,
                        jobApplicationDTO.getFullName(),
                        jobApplicationDTO.getPhoneNumber(),
                        jobApplicationDTO.getEmail(),
                        jobApplicationDTO.getAddress(),
                        jobApplicationDTO.getJob(),
                        jobApplicationDTO.getSkills(),
                        jobApplicationDTO.getLanguages(),
                        login
                )
        ));
    }

    public boolean deleteApplication (JSONIdModel id){

        JobApplication jobApplication = jobApplicationRepository.getOne(id.getId());

        if (jobApplication != null){
            jobApplicationRepository.delete(jobApplication);
            return true;
        }else {
            return false;
        }
    }

    public static JobApplicationDTO mapToDTO(JobApplication jobApplication){
        if (jobApplication != null){
            return new JobApplicationDTO(
                    jobApplication.getId(),
                    jobApplication.getFullName(),
                    jobApplication.getPhoneNumber(),
                    jobApplication.getEmail(),
                    jobApplication.getAddress(),
                    jobApplication.getJob(),
                    jobApplication.getSkills(),
                    jobApplication.getLanguages(),
                    LoginService.mapToDTO(jobApplication.getLogin()));
        }
        return null;
    }

}
