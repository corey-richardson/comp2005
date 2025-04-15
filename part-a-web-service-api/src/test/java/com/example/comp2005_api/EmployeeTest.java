package com.example.comp2005_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeTest
{
    @Test
    void CreateEmployeeTest() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Robin");
        employee.setLastName("Hood");

        assertNotNull(employee);
        assertEquals(1, employee.getId());
        assertEquals("Robin", employee.getFirstName());
        assertEquals("Hood", employee.getLastName());
        // assertEquals("Robin Hood", employee.getNameRepr());
    }
}