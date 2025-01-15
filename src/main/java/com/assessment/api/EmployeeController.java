package com.assessment.api;

import com.assessment.domain.Employee;
import com.assessment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public Mono<Employee> getEmployee(@PathVariable String employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping("/allocate")
    public Mono<Employee> allocateEmployee(@RequestParam String employeeId,
                                           @RequestParam String projectId,
                                           @RequestParam float allocation) {
        return employeeService.allocateEmployeeToProject(employeeId, projectId, allocation);
    }

    @PutMapping("/allocate")
    public Mono<Employee> modifyAllocation(@RequestParam String employeeId,
                                           @RequestParam String projectId,
                                           @RequestParam float newAllocation) {
        return employeeService.modifyAllocation(employeeId, projectId, newAllocation);
    }
    
}
