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

    @Test
    void testPersonEmptyConstructor() throws TimeConflictException {
        Person p = new Person();
        assertEquals("", p.getName());
    }


    @Test
    void testAddMeetingOnPerson() throws TimeConflictException {
        Person alice = new Person("Altai");
        Meeting m = new Meeting(20, 10, 9, 10);
        assertThrows(TimeConflictException.class, () -> alice.addMeeting(m));
    }

    @Test
    void testPrintAgendaOfPerson() throws TimeConflictException {
        Person alice = new Person("Altai");
        Meeting m = new Meeting(10, 10, 9, 10,  new ArrayList<>(), new Room("2A01"), "Agenda");
        alice.addMeeting(m);
        m.setDescription("Let's go");
        String agenda = alice.printAgenda(10, 10);
        assertTrue(agenda.contains("Let's go"));

        String agendaMonth = alice.printAgenda(10);
        assertTrue(agendaMonth.contains("Let's go"));
    }

    @Test
    void testGetMeeting() throws TimeConflictException {
        Person alice = new Person("Altai");
        Meeting m = new Meeting(10, 10, 9, 10,  new ArrayList<>(), new Room("2A01"), "Agenda");
        alice.addMeeting(m);
        m.setDescription("Let's go");

        assertNotNull(alice.getMeeting(10, 10, 0));
    }

    @Test
    void testRemoveMeeting() throws TimeConflictException {
        Person alice = new Person("Altai");
        Meeting m = new Meeting(10, 10, 9, 10,  new ArrayList<>(), new Room("2A01"), "Agenda");
        alice.addMeeting(m);
        m.setDescription("Let's go");

        Meeting m2 = new Meeting(10, 10, 11, 12,  new ArrayList<>(), new Room("2A023"), "Agenda");
        alice.addMeeting(m2);
        m2.setDescription("Let's go");

        alice.removeMeeting(10, 10, 1);
        assertThrows(IndexOutOfBoundsException.class, () -> alice.getMeeting(10, 10, 1));
    }
}
