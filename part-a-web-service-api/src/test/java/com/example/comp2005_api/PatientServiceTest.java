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
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class ReadmittedWithinSevenDaysTests {
        private Admission[] mockAdmissions;


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
        void testBoundaryBehaviour() {
            // Arrange
            Patient patientOne = new Patient(1, "LESS", "THAN", "06235959");
            Patient patientTwo = new Patient(2, "EXACTLY", "SEVEN", "07000000");
            Patient patientThree = new Patient(3, "MORE", "THAN", "07000001");

            Admission firstAdmission_patientOne = new Admission(1, 1, "2024-12-31T00:00:00", "2025-01-01T00:00:00");
            Admission firstAdmission_patientTwo = new Admission(2, 2, "2024-12-31T00:00:00", "2025-01-01T00:00:00");
            Admission firstAdmission_patientThree = new Admission(3, 3, "2024-12-31T00:00:00", "2025-01-01T00:00:00");

            Admission readmitWithinSevenDays = new Admission(4, 1, "2025-01-07T23:59:59", null);  // < 7 days, INCLUDE
            Admission readmitExactlySevenDays = new Admission(5, 2, "2025-01-08T00:00:00", null); // = 7 days, INCLUDE
            Admission readmitAfterSevenDays = new Admission(6, 3, "2025-01-08T00:00:01", null);   // > 7 days, EXCLUDE

            mockPatients = new Patient[] { patientOne, patientTwo, patientThree };
            mockAdmissions = new Admission[] { firstAdmission_patientOne, firstAdmission_patientTwo, firstAdmission_patientThree, readmitWithinSevenDays, readmitExactlySevenDays, readmitAfterSevenDays };

            when(apiHelper.getAllPatients()).thenReturn(mockPatients);
            when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);

            // Act
            List<Patient> patients = service.getPatientsReadmittedSevenDays();

            // Assert
            assertNotNull(patients);
            assertEquals(2, patients.size());
            assertEquals(1, patients.get(0).getId());
            assertEquals(2, patients.get(1).getId());

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
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class MultipleStaffTests {

        private Admission[] mockAdmissions;
        private Allocation[] mockAllocations;

        @BeforeEach
        void setUp() {

            Admission a1 = new Admission(); a1.setId(1); a1.setPatientID(1);
            Admission a2 = new Admission(); a2.setId(2); a2.setPatientID(2);

            mockAdmissions = new Admission[] { a1, a2 };

            Allocation alloc1 = new Allocation(); alloc1.setAdmissionId(1); alloc1.setEmployeeId(101);
            Allocation alloc2 = new Allocation(); alloc2.setAdmissionId(1); alloc2.setEmployeeId(102);
            Allocation alloc3 = new Allocation(); alloc3.setAdmissionId(2); alloc3.setEmployeeId(103);

            mockAllocations = new Allocation[] { alloc1, alloc2, alloc3 };
        }

        @Test
        void returnsExpected() {
            when(apiHelper.getAllPatients()).thenReturn(mockPatients);
            when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);
            when(apiHelper.getAllAllocations()).thenReturn(mockAllocations);

            List<Patient> result = service.getPatientsMultipleStaff();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(1, result.get(0).getId());
            assertEquals("Viv", result.get(0).getFirstName());
        }

        @Test
        void returnsExpectedDifferentAdmissions() {
            // Tests that it finds patients with multiple staff across separate admissions
            // Here, "Heather" (ID:2) has two admissions (IDs:2,3) where each had a different staff member (IDs:103,104)
            Admission a1 = new Admission(); a1.setId(1); a1.setPatientID(1);
            Admission a2 = new Admission(); a2.setId(2); a2.setPatientID(2);
            Admission a3 = new Admission(); a3.setId(3); a3.setPatientID(2);

            mockAdmissions = new Admission[] { a1, a2, a3 };

            Allocation alloc1 = new Allocation(); alloc1.setAdmissionId(1); alloc1.setEmployeeId(101);
            Allocation alloc2 = new Allocation(); alloc2.setAdmissionId(1); alloc2.setEmployeeId(102);
            Allocation alloc3 = new Allocation(); alloc3.setAdmissionId(2); alloc3.setEmployeeId(103);
            Allocation alloc4 = new Allocation(); alloc4.setAdmissionId(3); alloc4.setEmployeeId(104);

            mockAllocations = new Allocation[] { alloc1, alloc2, alloc3, alloc4 };

            when(apiHelper.getAllPatients()).thenReturn(mockPatients);
            when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);
            when(apiHelper.getAllAllocations()).thenReturn(mockAllocations);

            List<Patient> result = service.getPatientsMultipleStaff();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(2, result.get(1).getId());
            assertEquals("Heather", result.get(1).getFirstName());
        }

        @Test
        void returnsEmptyWhenNoMultiples() {
            when(apiHelper.getAllPatients()).thenReturn(mockPatients);
            when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);

            mockAllocations = Arrays.copyOfRange(mockAllocations, 1, mockAllocations.length);
            when(apiHelper.getAllAllocations()).thenReturn(mockAllocations);

            List<Patient> result = service.getPatientsMultipleStaff();

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void handlesErrorsGracefully() {
            when(apiHelper.getAllPatients()).thenThrow(new RuntimeException("Mock Error"));
            when(apiHelper.getAllAdmissions()).thenThrow(new RuntimeException("Mock Error"));
            when(apiHelper.getAllAllocations()).thenThrow(new RuntimeException("Mock Error"));

            List<Patient> result = service.getPatientsReadmittedSevenDays();

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }
}