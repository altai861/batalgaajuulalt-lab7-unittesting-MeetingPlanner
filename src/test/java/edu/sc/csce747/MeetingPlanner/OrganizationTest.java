package edu.sc.csce747.MeetingPlanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class OrganizationTest {

    /** 
     * Test: Get existing room
     * Description: Should successfully retrieve a room by ID.
     */
    @Test
    void testGetRoom() throws Exception {
        Organization org = new Organization();
        Room r = org.getRoom("2A01");
        assertEquals("2A01", r.getID());
    }

    /** 
     * Test: Get non-existing room
     * Description: Should throw Exception for room not found.
     */
    @Test
    void testGetRoomInvalid() {
        Organization org = new Organization();
        assertThrows(Exception.class, () -> org.getRoom("InvalidID"));
    }

    /** 
     * Test: Get existing employee
     * Description: Should successfully retrieve an employee by name.
     */
    @Test
    void testGetEmployee() throws Exception {
        Organization org = new Organization();
        Person p = org.getEmployee("Greg Gay");
        assertEquals("Greg Gay", p.getName());
    }

    /** 
     * Test: Get non-existing employee
     * Description: Should throw Exception for employee not found.
     */
    @Test
    void testGetEmployeeInvalid() {
        Organization org = new Organization();
        assertThrows(Exception.class, () -> org.getEmployee("Unknown Name"));
    }
}
