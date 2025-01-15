package com.assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAllocationEmail(String employeeEmail, String projectName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(employeeEmail);
        message.setSubject("Project Allocation");
        message.setText("You have been allocated to project: " + projectName);
        mailSender.send(message);
    }
}
