package com.assessment.service;

import com.assessment.domain.Employee;
import com.assessment.repository.EmployeeRepository;
import com.assessment.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmailService emailService;

    public Mono<Employee> getEmployeeById(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }

    public Mono<Employee> allocateEmployeeToProject(String employeeId, String projectId, float allocation) {
        return employeeRepository.findByEmployeeId(employeeId)
                                 .flatMap(employee -> projectRepository.findByProjectName(projectId)
                                                                       .flatMap(project -> {
                                                                           if (allocation <= 1.0f) {
                                                                               project.setAllocation(allocation);
                                                                               emailService.sendAllocationEmail(employee.getEmployeeName(), project.getProjectName());
                                                                               return employeeRepository.save(employee);
                                                                           } else {
                                                                               return Mono.error(new IllegalArgumentException("Invalid allocation"));
                                                                           }
                                                                       }));
    }

    public Mono<Employee> modifyAllocation(String employeeId, String projectId, float newAllocation) {
        return employeeRepository.findByEmployeeId(employeeId)
                                 .flatMap(employee -> projectRepository.findByProjectName(projectId)
                                                                       .flatMap(project -> {
                                                                           project.setAllocation(newAllocation);
                                                                           emailService.sendAllocationEmail(employee.getEmployeeName(), project.getProjectName());
                                                                           return projectRepository.save(project)
                                                                                                   .then(Mono.just(employee));
                                                                       }));
    }

}
