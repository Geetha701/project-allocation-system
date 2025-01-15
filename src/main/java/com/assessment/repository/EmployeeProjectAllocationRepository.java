package com.assessment.repository;

import com.assessment.domain.EmployeeProjectAllocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeProjectAllocationRepository extends MongoRepository<EmployeeProjectAllocation, String> {
    List<EmployeeProjectAllocation> findByEmployeeId(String employeeId);

}
