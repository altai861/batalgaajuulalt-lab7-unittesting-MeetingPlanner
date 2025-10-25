package edu.sc.csce747.MeetingPlanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PersonTest {

    /** 
     * Test: Add meeting to person's calendar
     * Description: Person can successfully add a meeting to their calendar.
     */
    @Test
    void testAddMeeting() throws TimeConflictException {
        Person p = new Person("Alice");
        Meeting m = new Meeting(1, 10, 9, 10);
        p.addMeeting(m);
        assertTrue(p.isBusy(1, 10, 9, 9));
    }

    /** 
     * Test: Person isBusy throws TimeConflictException for invalid time
     * Description: Using invalid month/day should raise exception.
     */
    @Test
    void testIsBusyInvalid() {
        Person p = new Person("Alice");
        assertThrows(TimeConflictException.class, () -> p.isBusy(0, 10, 9, 10));
        assertThrows(TimeConflictException.class, () -> p.isBusy(1, 32, 9, 10));
    }
}
