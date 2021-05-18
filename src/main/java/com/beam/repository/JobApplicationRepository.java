package com.beam.repository;

import com.beam.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository  extends JpaRepository<JobApplication,Integer> {
}
