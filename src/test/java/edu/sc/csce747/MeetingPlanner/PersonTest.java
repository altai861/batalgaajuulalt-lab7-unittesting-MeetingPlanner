package edu.sc.csce747.MeetingPlanner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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
    
    @Test
    void testVacationScheduling() throws TimeConflictException {
        Person alice = new Person("Alice");

        Meeting singleDayVacation = new Meeting(1, 10, 0, 23, new ArrayList<>(), new Room("2A01"), "Vacation");
        alice.addMeeting(singleDayVacation);
  
        assertTrue(alice.isBusy(1, 10, 0, 23), "Alice should be busy for single-day vacation");

        for (int day = 15; day <= 17; day++) {
            Meeting multiDayVacation = new Meeting(1, day, 0, 23, new ArrayList<>(), new Room("2A01"), "Vacation");
            alice.addMeeting(multiDayVacation);
        }
        for (int day = 15; day <= 17; day++) {
            assertTrue(alice.isBusy(1, day, 0, 23), "Alice should be busy for day " + day + " of multi-day vacation");
        }
    }
}
