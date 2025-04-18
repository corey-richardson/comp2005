// Carry out integration testing on the Controller layer

package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

@SpringBootTest
class PatientControllerTest
{
    @Autowired
    private PatientController patientController;
    @MockitoBean
    private PatientService patientService;
    private List<Patient> mockPatients;

    @BeforeEach
    void setUp() {
        // TODO
    }

    // TODO
}
