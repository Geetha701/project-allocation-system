package com.assessment.service;

import com.assessment.domain.Employee;
import com.assessment.domain.EmployeeProjectAllocation;
import com.assessment.domain.Project;
import com.assessment.repository.EmployeeProjectAllocationRepository;
import com.assessment.repository.EmployeeRepository;
import com.assessment.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeProjectAllocationService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeProjectAllocationRepository allocationRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmployeeProjectAllocation allocateEmployeeToProject(String employeeId, String projectId, float allocation) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();

        EmployeeProjectAllocation allocationEntry = new EmployeeProjectAllocation();
        allocationEntry.setEmployee(employee);
        allocationEntry.setProject(project);
        allocationEntry.setAllocation(allocation);

        allocationRepository.save(allocationEntry);

        sendEmailNotification(employee.getEmployeeName(), project.getProjectName(), employee.getEmployeeId());

        return allocationEntry;
    }

    public EmployeeProjectAllocation modifyProjectAllocation(String allocationId, float newAllocation) {
        EmployeeProjectAllocation allocation = allocationRepository.findById(allocationId).orElseThrow();
        allocation.setAllocation(newAllocation);
        return allocationRepository.save(allocation);
    }

    public Employee getSecondMostExperiencedPerson(String projectId) {
        List<Employee> employees = employeeRepository.findEmployeesByProjectId(projectId);
        employees.sort((e1, e2) -> Integer.compare(e2.getOverallExperience(), e1.getOverallExperience()));
        return employees.size() >= 2 ? employees.get(1) : null;
    }

    public List<Employee> getEmployeesBySkills(String primarySkill, String secondarySkill) {
        return employeeRepository.findByPrimarySkillAndSecondarySkill(primarySkill, secondarySkill);
    }

    public List<Employee> getEmployeesNotAllocatedWithPrimarySkill(String primarySkill) {
        List<Employee> employeesWithPrimarySkill = employeeRepository.findByPrimarySkill(primarySkill);
        return employeesWithPrimarySkill.stream()
                                        .filter(employee -> !isAllocatedToAnyProject(employee))
                                        .collect(Collectors.toList());
    }
    private boolean isAllocatedToAnyProject(Employee employee) {
        List<EmployeeProjectAllocation> allocations = allocationRepository.findByEmployeeId(employee.getId());
        return !allocations.isEmpty();
    }

    private void sendEmailNotification(String employeeName, String projectName, String employeeEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(employeeEmail);
        message.setSubject("New Project Allocation");
        message.setText("Dear " + employeeName + ",\n\nYou have been allocated to the project: " + projectName);
        emailSender.send(message);
    }
}
