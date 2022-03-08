package com.pa.laborator2;

import java.util.Comparator;
import java.util.Vector;

public class RoomAssignmentProblemSolver {

    static RoomAssignmentProblemSolution solveGreedy(RoomAssignmentProblemInstance problemInstance) {
        RoomAssignmentProblemSolution solution = new RoomAssignmentProblemSolution();
        Vector<Room> rooms = problemInstance.getRooms();
        Vector<Event> events = problemInstance.getEvents();

        rooms.sort(Comparator.comparingInt(Room::getCapacity));

        events.sort((o1, o2) -> {
            int result = Integer.compare(o1.getSize(), o2.getSize());
            if( result == 0 ) {
                return Integer.compare(o1.getEnd(), o2.getEnd());
            }
            return result;
        });

        for(Event ev : events) {
            for(Room rm : rooms) {
                if(solution.addAssignment(new Assignment(ev, rm)))
                    break;
            }
        }

        return solution;
    }

}
