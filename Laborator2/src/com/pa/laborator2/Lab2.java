package com.pa.laborator2;

public class Lab2 {

    public static void main(String[] args) {
        Lab2 lab2 = new Lab2();

        //lab2.compulsory();
        lab2.homework();
    }

    public void compulsory() {

        Event[] events = new Event[5];
        events[0] = new Event("C1", 100, 8, 10);
        events[1] = new Event("C2", 100, 10, 12);
        events[2] = new Event("L1", 30, 8, 10);
        events[3] = new Event("L2", 30, 8, 10);
        events[4] = new Event("L3", 30, 10, 12);

        Room[] rooms = new Room[4];
        /* Broken by making Room an abstract class
        rooms[0] = new Room(401, 30, RoomType.LAB);
        rooms[1] = new Room(403, 30, RoomType.LAB);
        rooms[2] = new Room(405, 30, RoomType.LAB);
        rooms[3] = new Room(309, 100, RoomType.LECTURE_HALL);
         */
        for(Event event : events){
            System.out.println(event);
        }
        /* Now throws a warning
        for(Room room : rooms) {
            System.out.println(room);
        }
        */
    }

    public void homework() {
        RoomAssignmentProblemInstance problemInstance = new RoomAssignmentProblemInstance();
        problemInstance.addAllEvents(new Event[]{
                new Event("C1", 100, 8, 10),
                new Event("C2", 100, 10, 12),
                new Event("L1", 30, 8, 10),
                new Event("L2", 30, 8, 10),
                new Event("L3", 30, 10, 12)
        });

        problemInstance.addAllRooms(new Room[]{
                new ComputerLab(401, 30, OperatingSystem.LINUX),
                new ComputerLab(403, 30, OperatingSystem.LINUX),
                new ComputerLab(405, 30, OperatingSystem.LINUX),
                new LectureHall(309, 100, true)
        });

        RoomAssignmentProblemSolution solution = RoomAssignmentProblemSolver.solveGreedy(problemInstance);

        //System.out.println(solution.toString());
        System.out.println("Solution: ");
        for(Assignment assignment : solution.getAssignments() ) {
            System.out.println(assignment.getRoom() + ":" + assignment.getEvent());
        }
    }
}
