package com.assessment.repository;

import com.assessment.domain.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
    Mono<Project> findByProjectName(String projectName);
}
