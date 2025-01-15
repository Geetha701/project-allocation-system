package com.assessment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "employees")
@Data
public class Employee {
    @Id
    private String employeeId;
    private String employeeName;
    private CapabilityCentre capabilityCentre;
    private LocalDate dateOfJoining;
    private Designation designation;
    private String primarySkill;
    private String secondarySkill;
    private int overallExperience;
}

enum CapabilityCentre {
    PRODUCT_AND_PLATFORM, DEP_CLOUD, DEVAA, DEP_QUALITY
}

enum Designation {
    PRINCIPAL_ENGINEER, STAFF_ENGINEER, TECHNICAL_LEAD, ARCHITECT, SENIOR_ENGINEER, ENGINEER, ASSOC_ENGINEER
}
