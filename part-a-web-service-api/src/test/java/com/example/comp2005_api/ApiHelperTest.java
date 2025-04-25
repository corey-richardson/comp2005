package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ApiHelperTest
{

    @MockitoBean
    private RestTemplate restTemplate;

    @Autowired
    private ApiHelper apiHelper;

    private Admission mockAdmission = new Admission();
    private Allocation mockAllocation = new Allocation();
    private Employee mockEmployee = new Employee();
    private Patient mockPatient = new Patient();

    private Admission[] mockAdmissions;
    private Allocation[] mockAllocations;
    private Employee[] mockEmployees;
    private Patient[] mockPatients;

    @Nested
    class AdmissionTests {

        @BeforeEach
        void setUp() {
            Admission a1 = new Admission();
            a1.setId(1);
            a1.setPatientID(1);

            Admission a2 = new Admission();
            a2.setId(2);
            a2.setPatientID(2);

            mockAdmissions = new Admission[]{a1, a2};
            mockAdmission = a1;
        }

        @Test
        void getAllAdmissions_returnsAdmissions() {
            // Arrange
            when(restTemplate.getForObject("https://web.socem.plymouth.ac.uk/COMP2005/api/Admissions", Admission[].class))
                    .thenReturn(mockAdmissions);
            // Act
            Admission[] admissions = apiHelper.getAllAdmissions();
            // Assert
            assertNotNull(admissions);
            assertEquals(2, admissions.length);
            assertEquals(1, admissions[0].getPatientID());
        }

        @Test
        void getAdmissionById_returnsAdmission() {
            when(restTemplate.getForObject("https://web.socem.plymouth.ac.uk/COMP2005/api/Admissions/1", Admission.class))
                    .thenReturn(mockAdmission);
            Admission admission = apiHelper.getAdmissionById(1);
            assertNotNull(admission);
            assertEquals(1, admission.getId());
        }

        @Test
        void getAdmissionById_handles404() {
            when(restTemplate.getForObject("https://web.socem.plymouth.ac.uk/COMP2005/api/Admissions/999", Admission.class))
                    .thenThrow(new org.springframework.web.client.HttpClientErrorException(HttpStatus.NOT_FOUND));
            Admission admission = apiHelper.getAdmissionById(999);
            assertNull(admission, "Admission should be null when the ID does not exist.");
        }
    }

    @Nested
    class AllocationTests {

        @BeforeEach
        void setUp() {
            Allocation alloc1 = new Allocation();
            alloc1.setAdmissionId(1);
            alloc1.setEmployeeId(101);

            Allocation alloc2 = new Allocation();
            alloc2.setAdmissionId(1);
            alloc2.setEmployeeId(102);

            mockAllocations = new Allocation[]{alloc1, alloc2};
            mockAllocation = alloc1;
        }

        // TODO
    }

    @Nested
    class EmployeeTests {

        @BeforeEach
        void setUp() {
            Employee e1 = new Employee();
            e1.setId(101);
            e1.setFirstName("Mike");

            Employee e2 = new Employee();
            e2.setId(102);
            e2.setFirstName("Mathias");

            mockEmployees = new Employee[]{e1, e2};
            mockEmployee = e1;
        }

        // TODO
    }

    @Nested
    class PatientTests {

        @BeforeEach
        void setUp() {
            // Set up mock data
            Patient p1 = new Patient();
            p1.setId(1);
            p1.setFirstName("Viv");
            p1.setLastName("Robinson");
            p1.setNhsNumber("1113335555");

            Patient p2 = new Patient();
            p2.setId(2);
            p2.setFirstName("Heather");
            p2.setLastName("Carter");
            p2.setNhsNumber("2224446666");

            mockPatients = new Patient[]{p1, p2};
            mockPatient = p1;
        }

        @Test
        void getAllPatients_returnsPatients() {
            // Arrange
            when(restTemplate.getForObject("https://web.socem.plymouth.ac.uk/COMP2005/api/Patients", Patient[].class))
                    .thenReturn(mockPatients);
            // Act
            Patient[] patients = apiHelper.getAllPatients();
            // Assert
            assertNotNull(patients);
            assertEquals(2, patients.length);
            assertEquals("Viv", patients[0].getFirstName());
            assertEquals("Heather", patients[1].getFirstName());
        }

        @Test
        void getPatientById_returnsPatient() {
            when(restTemplate.getForObject("https://web.socem.plymouth.ac.uk/COMP2005/api/Patients/1", Patient.class))
                    .thenReturn(mockPatient);
            Patient patient = apiHelper.getPatientById(1);
            assertNotNull(patient);
            assertEquals(1, patient.getId());
            assertEquals("Viv", patient.getFirstName());
        }

        @Test
        void getPatientById_handles404() {
            when(restTemplate.getForObject("https://web.socem.plymouth.ac.uk/COMP2005/api/Patients/999", Patient.class))
                    .thenThrow(new org.springframework.web.client.HttpClientErrorException(HttpStatus.NOT_FOUND));
            Patient patient = apiHelper.getPatientById(999);
            assertNull(patient, "Patient should be null when the ID does not exist.");
        }
    }
}
