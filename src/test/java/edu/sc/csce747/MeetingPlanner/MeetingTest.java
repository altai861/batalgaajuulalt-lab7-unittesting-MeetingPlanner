package edu.sc.csce747.MeetingPlanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class MeetingTest {

    /** 
     * Test: Meeting constructor and getters
     * Description: Verifies all fields are correctly set and retrieved.
     */
    @Test
    void testMeetingConstructorAndGetters() {
        ArrayList<Person> attendees = new ArrayList<>();
        Room room = new Room("2A01");
        Meeting m = new Meeting(1, 15, 10, 11, attendees, room, "Team Meeting");
        assertEquals(1, m.getMonth());
        assertEquals(15, m.getDay());
        assertEquals(10, m.getStartTime());
        assertEquals(11, m.getEndTime());
        assertEquals(room, m.getRoom());
        assertEquals("Team Meeting", m.getDescription());
        assertEquals(attendees, m.getAttendees());
    }

    /** 
     * Test: Add and remove attendee
     * Description: Ensures attendees can be added and removed from a meeting.
     */
    @Test
    void testAddRemoveAttendee() {
        Person p = new Person("John Doe");
        ArrayList<Person> attendees = new ArrayList<>();
        Meeting m = new Meeting(1, 15, 10, 11, attendees, new Room(), "Meeting");
        m.addAttendee(p);
        assertTrue(m.getAttendees().contains(p));
        m.removeAttendee(p);
        assertFalse(m.getAttendees().contains(p));
    }
}
