package com.assessment.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectAllocationProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendAllocationEvent(String employeeId, String projectId) {
        String message = "Employee " + employeeId + " allocated to project " + projectId;
        kafkaTemplate.send("project-allocation-topic", message);
    }
}
