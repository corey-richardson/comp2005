package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AllocationTest
{
    private static Allocation allocationParameterless;
    private static Allocation allocationConstructed;

    @BeforeAll
    static void setUp() {
        allocationParameterless = new Allocation();
        allocationConstructed = new Allocation(1, 2, 3, "2024-12-06", "2024-12-08");
    }

    @Test
    void testParameterlessNotNull() {
        assertNotNull(allocationParameterless);
    }

    @Test
    void testConstructedNotNull() {
        assertNotNull(allocationConstructed);
    }

    // Values for id, admissionId and employeeId should be unique to test ensure that the
    // getters aren't getting the value for a different field.

    @Test
    void testSetAndGetId() {
        allocationParameterless.setId(1);
        assertEquals(1, allocationParameterless.getId());
    }

    @Test
    void testSetAndGetAdmissionId() {
        allocationParameterless.setAdmissionId(2);
        assertEquals(2, allocationParameterless.getAdmissionId());
    }

    @Test
    void testSetAndGetEmployeeId() {
        allocationParameterless.setEmployeeId(3);
        assertEquals(3, allocationParameterless.getEmployeeId());
    }

    @Test
    void testSetAndGetStartTime() {
        allocationParameterless.setStartTime("2024-12-06");
        assertEquals("2024-12-06", allocationParameterless.getStartTime());
    }

    @Test
    void testSetAndGetEndTime() {
        allocationParameterless.setEndTime("2024-12-08");
        assertEquals("2024-12-08", allocationParameterless.getEndTime());
    }

    @Test
    void constructedAllocationTest() {
        assertNotNull(allocationConstructed);
        assertEquals(1, allocationConstructed.getId());
        assertEquals(2, allocationConstructed.getAdmissionId());
        assertEquals(3, allocationConstructed.getEmployeeId());
        assertEquals("2024-12-06", allocationConstructed.getStartTime());
        assertEquals("2024-12-08", allocationConstructed.getEndTime());
    }
}
