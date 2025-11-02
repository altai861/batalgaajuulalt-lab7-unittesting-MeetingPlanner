package edu.sc.csce747.MeetingPlanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

class CalendarTest {

    /** 
     * Test: Successfully adding a meeting and retrieving it
     * Description: Checks that addMeeting correctly stores a meeting and getMeeting retrieves it.
     */
    @Test
    void testAddAndGetMeeting() throws TimeConflictException {
        Calendar cal = new Calendar();
        Meeting m = new Meeting(1, 15, 10, 11);
        cal.addMeeting(m);
        assertEquals(m, cal.getMeeting(1, 15, 0));
    }

    /** 
     * Test: Check isBusy returns correct busy status
     * Description: Ensures isBusy returns true during the meeting and false outside.
     */
    @Test
    void testIsBusyTrueAndFalse() throws TimeConflictException {
        Calendar cal = new Calendar();
        Meeting m = new Meeting(1, 15, 10, 11);
        cal.addMeeting(m);
        assertTrue(cal.isBusy(1, 15, 10, 11));
        assertFalse(cal.isBusy(1, 15, 11, 12));
    }


    @Test
    void testIsBusyCheckEndTime() throws TimeConflictException {
        Calendar cal = new Calendar();
        Meeting m = new Meeting(1, 15, 10, 13);
        cal.addMeeting(m);
        assertTrue(cal.isBusy(1, 15, 9, 12));
    }

    @Test
    void checkCheckTimesIllegalHour() throws TimeConflictException {
        Calendar cal = new Calendar();
        assertThrows(TimeConflictException.class, () -> cal.checkTimes(5, 23, 20, 24));
    }

    @Test
    void testAddMeetingOverlapStartTime() throws TimeConflictException {
        Calendar cal = new Calendar();
        Meeting m1 = new Meeting(10, 5, 10, 14);
        cal.addMeeting(m1);
        m1.setDescription("My birthday meeting");

        Meeting m2 = new Meeting(10, 5, 12, 15);
        m2.setDescription("My second birthday meeting");

        assertThrows(TimeConflictException.class, () -> cal.addMeeting(m2));
    }

    @Test
    void testAddMeetingOverlapEndTime() throws TimeConflictException {
        Calendar cal = new Calendar();
        Meeting m1 = new Meeting(10, 5, 10, 14);
        m1.setDescription("My birthday meeting");

        cal.addMeeting(m1);

        Meeting m2 = new Meeting(10, 5, 9, 13);
        m2.setDescription("My second birthday meeting");

        assertThrows(TimeConflictException.class, () -> cal.addMeeting(m2));
    }


    @Test
    void testPrintAgenda() throws TimeConflictException {
        Person me = new Person("Altai");
        Person friend = new Person("Khangal");
        ArrayList<Person> attendees = new ArrayList<Person>();
        Room livingRoom = new Room("Living Room");
        Room meetingRoom = new Room("MR 1");
        attendees.add(me);
        attendees.add(friend);
        // Arrange
        Calendar cal = new Calendar();
        cal.addMeeting(new Meeting(10, 5, 12, 13, attendees, livingRoom, "Birthday party"));
        cal.addMeeting(new Meeting(1, 10, 5, 7, attendees, meetingRoom, "Christmas Eve"));
        // Act
        String octoberAgenda = cal.printAgenda(10);
        String novemberAgenda = cal.printAgenda(1);

        // Assert
        assertTrue(octoberAgenda.contains("Agenda for 10:"), "Should include the correct month header");
        assertTrue(octoberAgenda.contains("Birthday party"), "Should list the October meeting");

        assertTrue(novemberAgenda.contains("Agenda for 1:"), "Should include the correct month header");
        assertTrue(novemberAgenda.contains("Christmas Eve"), "Should list the December meeting");
    }


    @Test
    void testPrintAgendaDay() throws TimeConflictException {
        Person me = new Person("Altai");
        Person friend = new Person("Khangal");
        ArrayList<Person> attendees = new ArrayList<Person>();
        Room livingRoom = new Room("Living Room");
        Room meetingRoom = new Room("MR 1");
        attendees.add(me);
        attendees.add(friend);
        // Arrange
        Calendar cal = new Calendar();
        cal.addMeeting(new Meeting(10, 5, 12, 13, attendees, livingRoom, "Birthday party"));
        cal.addMeeting(new Meeting(10, 5, 5, 7, attendees, meetingRoom, "Christmas Eve"));
        // Act
        String octoberFifthAgenda = cal.printAgenda(10, 5);

        // Assert
        assertTrue(octoberFifthAgenda.contains("Agenda for 10/5:"), "Should include the correct month header");
        assertTrue(octoberFifthAgenda.contains("Birthday party"), "Should list the October meeting");
        assertTrue(octoberFifthAgenda.contains("Christmas Eve"), "Should list the October meeting");
    }

    /** 
     * Test: Clear schedule removes all meetings for a day
     * Description: Ensures that after clearing, the day has no meetings.
     */
    @Test
    void testClearSchedule() throws TimeConflictException {
        Calendar cal = new Calendar();
        Meeting m = new Meeting(1, 15, 10, 11);
        cal.addMeeting(m);
        cal.clearSchedule(1, 15);
        assertEquals(0, cal.printAgenda(1, 15).length() - ("Agenda for 1/15:\n".length()));
    }

    /** 
     * Test: Remove meeting successfully
     * Description: Checks that a meeting can be removed and is no longer in the calendar.
     */
    @Test
    void testRemoveMeeting() throws TimeConflictException {
        Calendar cal = new Calendar();
        Meeting m = new Meeting(1, 15, 10, 11);
        cal.addMeeting(m);
        cal.removeMeeting(1, 15, 0);
        assertEquals(0, cal.printAgenda(1, 15).length() - ("Agenda for 1/15:\n".length()));
    }

    /** 
     * Test: Add meeting with invalid day (February 35)
     * Description: Should throw TimeConflictException for invalid day.
     */
    @Test
    void testAddMeetingInvalidDay() {
        Calendar cal = new Calendar();
        Meeting m = new Meeting(2, 35, 10, 11);
        assertThrows(TimeConflictException.class, () -> cal.addMeeting(m));
    }

    /** 
     * Test: Add meeting with invalid month (13)
     * Description: Should throw TimeConflictException for invalid month.
     */
    @Test
    void testAddMeetingInvalidMonth() {
        Calendar cal = new Calendar();
        Meeting m = new Meeting(13, 10, 10, 11);
        assertThrows(TimeConflictException.class, () -> cal.addMeeting(m));
    }
    
    /** 
     * Test: Add meeting with valid month (12)
     * Description: Should successfully add meeting
     * @throws TimeConflictException 
     */
    @Test
    void testAddMeetingValidMonth() throws TimeConflictException {
        Calendar cal = new Calendar();
        Meeting m = new Meeting(11, 10, 10, 11);
        cal.addMeeting(m);
        assertNotNull(cal.getMeeting(11, 10, 0));
    }

    /** 
     * Test: Add meeting with illegal times
     * Description: Checks negative start time and end before start time.
     */
    @Test
    void testAddMeetingIllegalTime() {
        Calendar cal = new Calendar();
        Meeting m1 = new Meeting(1, 15, -1, 10);
        Meeting m2 = new Meeting(1, 15, 12, 10);
        assertThrows(TimeConflictException.class, () -> cal.addMeeting(m1));
        assertThrows(TimeConflictException.class, () -> cal.addMeeting(m2));
    }
    
    /** 
     * Test: Add meeting with illegal times
     * Description: Checks negative start time and end before start time.
     */
    @Test
    void testAddMeetingIllegalMonth() {
        Calendar cal = new Calendar();
        Meeting m1 = new Meeting(13, 15, 1, 10);
        assertThrows(TimeConflictException.class, () -> cal.addMeeting(m1));
    }

    /** 
     * Test: getMeeting with invalid index
     * Description: Should throw IndexOutOfBoundsException if index does not exist.
     */
    @Test
    void testGetMeetingInvalidIndex() {
        Calendar cal = new Calendar();
        assertThrows(IndexOutOfBoundsException.class, () -> cal.getMeeting(1, 15, 5));
    }
    
    /**
     * Test: getMeeting with illegal date data, should return TimeConflictException.
     */
    @Test
    void testGetMeetingOnInvalidDate() {
    	Calendar cal = new Calendar();
    	assertThrows(TimeConflictException.class, () -> cal.getMeeting(1, 40, 0));
    }

    /** 
     * Test: removeMeeting with invalid index
     * Description: Should throw IndexOutOfBoundsException if index does not exist.
     */
    @Test
    void testRemoveMeetingInvalidIndex() {
        Calendar cal = new Calendar();
        assertThrows(IndexOutOfBoundsException.class, () -> cal.removeMeeting(1, 15, 5));
    }


    @Test
    void testAddMeetingToNov30th() throws TimeConflictException {
        Calendar cal = new Calendar();
        Meeting m = new Meeting(11, 30, 10, 11);
        cal.addMeeting(m);

        // Check that a meeting exists at the given time
        assertNotNull(cal.getMeeting(11, 30, 0));
    }
    
    
    @Test
    void testMeetingThatEndsWithinTheHour() throws TimeConflictException {
    	Calendar cal = new Calendar();
    	Meeting m = new Meeting(11, 29, 10, 11);
    	cal.addMeeting(m);
    	assertNotNull(cal.getMeeting(11, 29, 0));
    }
}
