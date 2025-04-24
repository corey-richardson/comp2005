package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdmissionControllerTest
{
    @Mock
    private AdmissionService admissionService;

    @InjectMocks
    private AdmissionController admissionController;

    private List<Admission> mockAdmissions;

    @BeforeEach
    void setUpBase() {
        admissionController = new AdmissionController(admissionService);

        Admission a1 = new Admission(); a1.setPatientID(1); a1.setAdmissionDate("2023-01-01T10:00:00"); a1.setDischargeDate("2023-01-02T10:00:00");
        Admission a2 = new Admission(); a2.setPatientID(1); a2.setAdmissionDate("2023-01-05T12:00:00"); a2.setDischargeDate("2023-01-06T12:00:00");
        Admission a3 = new Admission(); a3.setPatientID(2); a3.setAdmissionDate("2023-01-01T14:00:00"); a3.setDischargeDate("2023-01-02T14:00:00");
        Admission a4 = new Admission(); a4.setPatientID(2); a4.setAdmissionDate("2023-01-10T18:00:00"); a4.setDischargeDate("2023-01-11T18:00:00");
        Admission a5 = new Admission(); a5.setId(5); a5.setPatientID(3); a5.setAdmissionDate("2023-02-01T09:00:00"); a5.setDischargeDate("2023-02-02T09:00:00");
        Admission a6 = new Admission(); a6.setId(6); a6.setPatientID(4); a6.setAdmissionDate("2023-02-15T10:00:00"); a6.setDischargeDate("2023-02-16T10:00:00");
        Admission a7 = new Admission(); a7.setId(7); a7.setPatientID(5); a7.setAdmissionDate("2023-03-01T11:00:00"); a7.setDischargeDate("2023-03-02T11:00:00");
        Admission a8 = new Admission(); a8.setId(8); a8.setPatientID(5); a8.setAdmissionDate("2023-03-20T12:00:00"); a8.setDischargeDate("2023-03-21T12:00:00");

        mockAdmissions = Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8);
    }

    @Nested
    class MonthWithMostAdmissionsTests {

        @Test
        void returnsExpectedMonth() {
            when(admissionService.getMonthWithMostAdmissions()).thenReturn("2023-01");
            String result = admissionController.getMonthWithMost();
            assertNotNull(result);
            assertEquals("2023-01", result);
        }

        @Test
        void returnsEmptyWhenNoAdmissions() {
            when(admissionService.getMonthWithMostAdmissions()).thenReturn("No admissions found.");

            String result = admissionService.getMonthWithMostAdmissions();

            assertNotNull(result);
            assertEquals("No admissions found.", result);
        }

        @Test
        void handlesErrorsGracefully() {
            // When the admissionService fails, it returns a fallback string
            when(admissionService.getMonthWithMostAdmissions()).thenReturn("No admissions found.");

            String result = admissionService.getMonthWithMostAdmissions();

            assertNotNull(result);
            assertEquals("No admissions found.", result);
        }
    }
}
