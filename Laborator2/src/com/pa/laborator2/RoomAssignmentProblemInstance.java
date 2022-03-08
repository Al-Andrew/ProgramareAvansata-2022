package com.pa.laborator2;

import java.util.Arrays;
import java.util.Vector;

public class RoomAssignmentProblemInstance {

    private final Vector<Room> rooms;
    private final Vector<Event> events;

    public RoomAssignmentProblemInstance() {
        rooms  = new Vector<>();
        events = new Vector<>();
    }

    public void addRoom(Room newRoom) {
        // Check if it's not a duplicate
        for(Room r : rooms ) {
            if(r == newRoom) {
                return; // Maybe should throw an exception?
            }
        }

        rooms.add(newRoom);
    }
    /// Utility function to add multiple rooms at once
    public void addAllRooms(Room[] newRooms) {
        for(Room newRoom : newRooms) {
            addRoom(newRoom);
        }
    }

    public void addEvent(Event newEvent) {
        // Check if it's not a duplicate
        for(Event e : events) {
            if(e == newEvent) {
                return;
            }
        }

        events.add(newEvent);
    }

    /// Utility function to add multiple events at once
    public void addAllEvents(Event[] newEvents) {
        for(Event newEvent : newEvents) {
            addEvent(newEvent);
        }
    }


    public Vector<Room> getRooms() {
        return rooms;
    }

    public Vector<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return "RoomAssignmentProblemInstance{" +
                "rooms=" + rooms +
                ", events=" + events +
                '}';
    }
}
