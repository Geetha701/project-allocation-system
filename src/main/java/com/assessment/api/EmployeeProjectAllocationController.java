package com.assessment.api;


import com.assessment.domain.Employee;
import com.assessment.domain.EmployeeProjectAllocation;
import com.assessment.service.EmployeeProjectAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allocation")
public class EmployeeProjectAllocationController {

    @Autowired
    private EmployeeProjectAllocationService allocationService;

    @PostMapping("/allocate")
    public EmployeeProjectAllocation allocateEmployeeToProject(@RequestParam String employeeId,
                                                               @RequestParam String projectId,
                                                               @RequestParam float allocation) {
        return allocationService.allocateEmployeeToProject(employeeId, projectId, allocation);
    }

    @PutMapping("/modify")
    public EmployeeProjectAllocation modifyProjectAllocation(@RequestParam String allocationId,
                                                             @RequestParam float newAllocation) {
        return allocationService.modifyProjectAllocation(allocationId, newAllocation);
    }

    @GetMapping("/second-most-experienced")
    public Employee getSecondMostExperiencedPerson(@RequestParam String projectId) {
        return allocationService.getSecondMostExperiencedPerson(projectId);
    }

    @GetMapping("/find-by-skills")
    public List<Employee> getEmployeesBySkills(@RequestParam String primarySkill,
                                               @RequestParam String secondarySkill) {
        return allocationService.getEmployeesBySkills(primarySkill, secondarySkill);
    }

    @GetMapping("/not-allocated")
    public List<Employee> getEmployeesNotAllocatedWithPrimarySkill(@RequestParam String primarySkill) {
        return allocationService.getEmployeesNotAllocatedWithPrimarySkill(primarySkill);
    }
}
