package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeTest
{
    private static Employee employeeParameterless;
    private static Employee employeeConstructed;

    @BeforeAll
    static void setUp() {
        employeeParameterless = new Employee();
        employeeConstructed = new Employee(42, "Robin", "Hood", null);
    }

    @Test
    void testParameterlessNotNull() {
        assertNotNull(employeeParameterless);
    }

    @Test
    void testConstructedNotNull() {
        assertNotNull(employeeConstructed);
    }

    @Test
    void testSetAndGetId() {
        employeeParameterless.setId(42);
        assertEquals(42, employeeParameterless.getId());
    }

    @Test
    void testSetAndGetFirstName() {
        employeeParameterless.setFirstName("Robin");
        assertEquals("Robin", employeeParameterless.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        employeeParameterless.setLastName("Hood");
        assertEquals("Hood", employeeParameterless.getLastName());
    }

    @Test
    void constructedEmployeeTest() {
        assertEquals(42, employeeConstructed.getId());
        assertEquals("Robin", employeeConstructed.getFirstName());
        assertEquals("Hood", employeeConstructed.getLastName());
    }
}
