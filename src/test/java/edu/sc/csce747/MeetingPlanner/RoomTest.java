package edu.sc.csce747.MeetingPlanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
}
