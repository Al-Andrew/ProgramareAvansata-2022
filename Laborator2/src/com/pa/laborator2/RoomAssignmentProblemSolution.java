package com.pa.laborator2;


import java.util.Arrays;
import java.util.Vector;

public class RoomAssignmentProblemSolution {
    private Vector<Assignment> assignments;

    public RoomAssignmentProblemSolution() {
        assignments = new Vector<>();
    }

    public RoomAssignmentProblemSolution(Assignment[] assignments) {
        this.assignments = new Vector<>();
        this.assignments.addAll(Arrays.asList(assignments));
    }

    public Vector<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Vector<Assignment> assignments) {
        this.assignments = assignments;
    }

    public boolean addAssignment(Assignment assignment) {
        for(Assignment ass : assignments) { // Check if that spot isn't taken
            if(ass.getRoom() == assignment.getRoom() && ass.getEvent() == assignment.getEvent() )
                return false;
        }
        if( assignment.getRoom().getCapacity() < assignment.getEvent().getSize() )
            return false;
        this.assignments.add(assignment);
        return true;
    }

    @Override
    public String toString() {
        return "RoomAssignmentProblemSolution{" +
                "assignments=" + assignments.toString() +
                '}';
    }
}


class Assignment {
    private Event event;
    private Room room;

    public Assignment(Event event, Room room) {
        this.event = event;
        this.room = room;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "event=" + getEvent() +
                ", room=" + getEvent() +
                '}';
    }
}