package com.johnny.service;

import com.johnny.controller.JobController;
import com.johnny.model.JobEntity;
import jakarta.servlet.http.HttpServletResponse;

public interface QuartzService {
    void addJob(JobEntity job, HttpServletResponse response, JobController jobController);
}