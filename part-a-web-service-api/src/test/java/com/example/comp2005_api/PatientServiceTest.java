// Carry out unit testing on the Service Layer

// Nested test suites
// https://www.baeldung.com/junit-5-nested-test-classes

package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceTest
{
    private ApiHelper apiHelper;
    private PatientService service;

    private Patient[] mockPatients;

    @BeforeEach
    void setUpBase() {
        apiHelper = mock(ApiHelper.class);
        service = new PatientService(apiHelper);

        Patient p1 = new Patient(); p1.setId(1); p1.setFirstName("Viv");
        Patient p2 = new Patient(); p2.setId(2); p2.setFirstName("Heather");

        mockPatients = new Patient[] { p1, p2 };
    }

    @Nested
    class NeverAdmittedTests {

        private Admission[] mockAdmissions;

        @BeforeEach
        void setUpAdmissions() {
            Admission a1 = new Admission(); a1.setPatientID(1);
            Admission a2 = new Admission(); a2.setPatientID(3);
            mockAdmissions = new Admission[] { a1, a2 };
        }

        @Test
        void returnsExpected() {
            when(apiHelper.getAllPatients()).thenReturn(mockPatients);
            when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);

            List<Patient> result = service.getPatientsNeverAdmitted();
            assertEquals(1, result.size());
            assertEquals("Heather", result.get(0).getFirstName());
        }

        @Test
        void returnsAllWhenNoAdmissions() {
            when(apiHelper.getAllPatients()).thenReturn(mockPatients);
            when(apiHelper.getAllAdmissions()).thenReturn(new Admission[0]);

            List<Patient> result = service.getPatientsNeverAdmitted();
            assertEquals(2, result.size());
        }

        @Test
        void returnsEmptyWhenNoPatients() {
            when(apiHelper.getAllPatients()).thenReturn(new Patient[0]);
            when(apiHelper.getAllAdmissions()).thenReturn(new Admission[0]);

            List<Patient> result = service.getPatientsNeverAdmitted();
            assertTrue(result.isEmpty());
        }

        @Test
        void handlesErrorsGracefully() {
            when(apiHelper.getAllPatients()).thenThrow(new RuntimeException("Mock Error"));
            when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);

            List<Patient> result = service.getPatientsNeverAdmitted();
            assertTrue(result.isEmpty());
        }
    }
}