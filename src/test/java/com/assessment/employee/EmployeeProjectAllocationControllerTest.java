package com.assessment.api;

import com.assessment.domain.Employee;
import com.assessment.domain.EmployeeProjectAllocation;
import com.assessment.service.EmployeeProjectAllocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class EmployeeProjectAllocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployeeProjectAllocationService allocationService;

    @InjectMocks
    private EmployeeProjectAllocationController allocationController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(allocationController).build();
    }

    @Test
    void testAllocateEmployeeToProject() throws Exception {
        // Given
        EmployeeProjectAllocation allocation = new EmployeeProjectAllocation("1", "123", 50f);
        when(allocationService.allocateEmployeeToProject(anyString(), anyString(), anyFloat())).thenReturn(allocation);

        // When & Then
        mockMvc.perform(post("/allocation/allocate")
                                .param("employeeId", "1")
                                .param("projectId", "123")
                                .param("allocation", "50"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.employeeId").value("1"))
               .andExpect(jsonPath("$.projectId").value("123"))
               .andExpect(jsonPath("$.allocation").value(50));

        verify(allocationService, times(1)).allocateEmployeeToProject(anyString(), anyString(), anyFloat());
    }

    @Test
    void testModifyProjectAllocation() throws Exception {
        // Given
        EmployeeProjectAllocation updatedAllocation = new EmployeeProjectAllocation("1", "123", 60f);
        when(allocationService.modifyProjectAllocation(anyString(), anyFloat())).thenReturn(updatedAllocation);

        // When & Then
        mockMvc.perform(put("/allocation/modify")
                                .param("allocationId", "1")
                                .param("newAllocation", "60"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.employeeId").value("1"))
               .andExpect(jsonPath("$.projectId").value("123"))
               .andExpect(jsonPath("$.allocation").value(60));

        verify(allocationService, times(1)).modifyProjectAllocation(anyString(), anyFloat());
    }

    @Test
    void testGetSecondMostExperiencedPerson() throws Exception {
        // Given
        Employee employee = new Employee("1", "John", "Java", "Project A", 5);
        when(allocationService.getSecondMostExperiencedPerson(anyString())).thenReturn(employee);

        // When & Then
        mockMvc.perform(get("/allocation/second-most-experienced")
                                .param("projectId", "123"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.employeeId").value("1"))
               .andExpect(jsonPath("$.name").value("John"))
               .andExpect(jsonPath("$.experience").value(5));

        verify(allocationService, times(1)).getSecondMostExperiencedPerson(anyString());
    }

    @Test
    void testGetEmployeesBySkills() throws Exception {
        // Given
        Employee employee = new Employee("1", "John", "Java", "Project A", 5);
        List<Employee> employees = List.of(employee);
        when(allocationService.getEmployeesBySkills(anyString(), anyString())).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/allocation/find-by-skills")
                                .param("primarySkill", "Java")
                                .param("secondarySkill", "Spring"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].employeeId").value("1"))
               .andExpect(jsonPath("$[0].name").value("John"))
               .andExpect(jsonPath("$[0].primarySkill").value("Java"));

        verify(allocationService, times(1)).getEmployeesBySkills(anyString(), anyString());
    }

    @Test
    void testGetEmployeesNotAllocatedWithPrimarySkill() throws Exception {
        // Given
        Employee employee = new Employee("1", "John", "Java", null, 5);
        List<Employee> employees = List.of(employee);
        when(allocationService.getEmployeesNotAllocatedWithPrimarySkill(anyString())).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/allocation/not-allocated")
                                .param("primarySkill", "Java"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].employeeId").value("1"))
               .andExpect(jsonPath("$[0].name").value("John"))
               .andExpect(jsonPath("$[0].primarySkill").value("Java"));

        verify(allocationService, times(1)).getEmployeesNotAllocatedWithPrimarySkill(anyString());
    }
}
