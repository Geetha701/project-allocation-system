package com.assessment.repository;

import com.assessment.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByPrimarySkillAndSecondarySkill(String primarySkill, String secondarySkill);
    List<Employee> findEmployeesByProjectId(String projectId);
    List<Employee> findByPrimarySkill(String primarySkill);

}
