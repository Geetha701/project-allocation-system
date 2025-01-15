package com.assessment.repository;

import com.assessment.domain.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
    Mono<Employee> findByEmployeeId(String employeeId);
}
