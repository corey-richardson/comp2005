// Carry out unit testing on the Service Layer

// Nested test suites
// https://www.baeldung.com/junit-5-nested-test-classes

package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
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

    @Nested
    class ReadmittedWithinSevenDaysTests {
        private Admission[] mockAdmissions;
        private Admission[] mockAdmissionsOnlyGT7Days;


        @BeforeEach
        void setUpAdmissions() {
            Admission a1 = new Admission();
            a1.setPatientID(1);
            a1.setAdmissionDate("2023-01-01T10:00:00");
            a1.setDischargeDate("2023-01-01T11:30:27");

            Admission a2 = new Admission();
            a2.setPatientID(1);
            a2.setAdmissionDate("2023-01-05T12:20:12");

            Admission a3 = new Admission();
            a3.setPatientID(2);
            a3.setAdmissionDate("2023-01-01T13:45:10");
            a3.setDischargeDate("2023-01-01T13:45:10");

            Admission a4 = new Admission();
            a4.setPatientID(2);
            a4.setAdmissionDate("2023-01-20T17:38:00");

            mockAdmissions = new Admission[] { a1, a2, a3, a4 };
            mockAdmissionsOnlyGT7Days = new Admission[] { a3, a4 };
        }

        @Test
        void returnsExpected() {
            when(apiHelper.getAllPatients()).thenReturn(mockPatients);
            when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);
            List<Patient> patients = service.getPatientsReadmittedSevenDays();
            assertNotNull(patients);
            assertEquals(1, patients.size());
            assertEquals(1, patients.get(0).getId());
        }

        @Test
        void returnsEmptyWhenNoPatients() {
            mockAdmissions = Arrays.copyOfRange(mockAdmissions, 2, mockAdmissions.length); // a3 and a4 only, >7 days
            when(apiHelper.getAllPatients()).thenReturn(mockPatients);
            when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);
            List<Patient> patients = service.getPatientsReadmittedSevenDays();
            assertNotNull(patients);
            assert(patients.isEmpty());
        }

        @Test
        void handlesErrorsGracefully() {
            when(apiHelper.getAllPatients()).thenThrow(new RuntimeException("Mock Error"));
            when(apiHelper.getAllAdmissions()).thenThrow(new RuntimeException("Mock Error"));

            List<Patient> result = service.getPatientsReadmittedSevenDays();
            assertTrue(result.isEmpty());
        }
    }
}