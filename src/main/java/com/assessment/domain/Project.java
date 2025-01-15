package com.assessment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "projects")
@Data
public class Project {
    @Id
    private String projectId;
    private AccountName accountName;
    private String projectName;
    private float allocation;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String remarks;
}

enum AccountName {
    ANCESTRY, BNYM, CALIBO_LLC, EXPERIAN, FORD, GUARANTEED_RATE, INVOICE_CLOUD,
    VATTIKUTI_VENTURES_LLC, ZIP_CO_US_INC, PAYPAL, JOHNSON_CONTROLS_INC, WESTERN_UNION
}
