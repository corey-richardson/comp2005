package com.example.comp2005_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AllocationTest
{
    @Test
    void CreateAllocationTest() {
        Allocation allocation = new Allocation();
        allocation.setId(1);
        allocation.setAdmissionId(2);
        allocation.setEmployeeId(3);
        allocation.setStartTime("2024-12-06");
        allocation.setEndTime("2024-12-08");

        assertNotNull(allocation);
        assertEquals(1, allocation.getId());
        assertEquals(2, allocation.getAdmissionId());
        assertEquals(3, allocation.getEmployeeId());
        assertEquals("2024-12-06", allocation.getStartTime());
        assertEquals("2024-12-08", allocation.getEndTime());
    }
}