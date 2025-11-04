package edu.sc.csce747.MeetingPlanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class RoomTest {

    /** 
     * Test: Add meeting to room
     * Description: Meeting is added to room calendar without conflict.
     */
    @Test
    void testAddMeeting() throws TimeConflictException {
        Room r = new Room("2A01");
        Meeting m = new Meeting(1, 15, 10, 11);
        r.addMeeting(m);
        assertTrue(r.isBusy(1, 15, 10, 11));
    }

    /** 
     * Test: Room isBusy for invalid time
     * Description: Should throw TimeConflictException for invalid hour.
     */
    @Test
    void testIsBusyInvalidTime() {
        Room r = new Room("2A01");
        assertThrows(TimeConflictException.class, () -> r.isBusy(1, 15, -1, 10));
    }

    @Test
    void testAddMeetingException() throws TimeConflictException {
        Room r = new Room("fff");
        Meeting m = new Meeting(20, 15, 10, 11);
        assertThrows(TimeConflictException.class, () -> r.addMeeting(m));
    }

    @Test
    void testPrintAgenda() throws TimeConflictException {
        Room r = new Room("fff");
        Meeting m = new Meeting(10, 15, 10, 11, new ArrayList<>(), r, "Let's go");
        m.setDescription("Let's go");
        r.addMeeting(m);
        assertTrue(r.printAgenda(10, 15).contains("Let's go"));
        assertTrue(r.printAgenda(10).contains("Let's go"));
    }

    @Test
    void testGetMeeting() throws TimeConflictException {
        Room r = new Room("fff");
        r.addMeeting(new Meeting(10, 15, 10, 11, new ArrayList<>(), r, "Let's go"));
        assertNotNull(r.getMeeting(10, 15, 0));
    }

    @Test
    void testRemoveMeeting() throws TimeConflictException {
        Room r = new Room("fff");
        r.addMeeting(new Meeting(10, 15, 10, 11, new ArrayList<>(), r, "Let's go"));
        r.removeMeeting(10, 15, 0);
        assertThrows(IndexOutOfBoundsException.class, () -> r.getMeeting(10, 15, 0));
    }
}
