package com.example.comp2005_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Admissions")
public class AdmissionController
{
    private final AdmissionService service;

    public AdmissionController(AdmissionService admissionService) {
        this.service = admissionService;
    }

    // F3 Endpoint
    @GetMapping("/month-with-most")
    public String getMonthWithMost() {
        // TODO
        return "";
    }
}