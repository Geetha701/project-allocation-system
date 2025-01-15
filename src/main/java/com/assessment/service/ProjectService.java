package com.assessment.service;

import com.assessment.domain.Project;
import com.assessment.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Mono<Project> createProject(Project project) {
        return projectRepository.save(project);
    }

    public Mono<Project> getProjectByName(String projectName) {
        return projectRepository.findByProjectName(projectName);
    }
}
