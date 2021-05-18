package com.beam.util;


import com.beam.model.JobApplication;
import com.beam.model.Login;
import com.beam.model.enums.Role;
import com.beam.repository.JobApplicationRepository;
import com.beam.repository.LoginRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitializerRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitializerRunner.class);

    LoginRepository loginRepository;

    PasswordEncoder passwordEncoder;

    JobApplicationRepository jobApplicationRepository;

    @Autowired
    public InitializerRunner(LoginRepository loginRepository, JobApplicationRepository jobApplicationRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public void run(String... args) throws Exception {

        Login admin = Login.builder().username("admin").password(passwordEncoder.encode("1234")).role(Role.ROLE_ADMIN).build();
        Login user = Login.builder().username("oguzhan").password(passwordEncoder.encode("1234")).role(Role.ROLE_USER).build();


        loginRepository.save(admin);
        loginRepository.save(user);

        JobApplication jobApplication = JobApplication.builder()
                .fullName("Oguzhan Olgun").phoneNumber("+90 505 436 95 76")
                .email("olgunoguzhan@gmail.com").address("Ankara").job("Java Developer")
                .skills("JavaEE, Spring Framework")
                .languages("English")
                .login(user)
                .build();

        jobApplicationRepository.save(jobApplication);

        loginRepository.findAll().forEach(user1 -> logger.info("{}",user1));

        jobApplicationRepository.findAll().forEach(app -> logger.info("{}",app));

    }
}
