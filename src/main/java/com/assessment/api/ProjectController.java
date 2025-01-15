package com.assessment.api;

import com.assessment.domain.Project;
import com.assessment.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/")
    public Mono<Project> createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping("/{projectName}")
    public Mono<Project> getProject(@PathVariable String projectName) {
        return projectService.getProjectByName(projectName);
    }
}
