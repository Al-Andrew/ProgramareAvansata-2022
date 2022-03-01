package com.pa.laborator2;

public class Room {
    private int number;
    private int capacity;
    private RoomType type;

    public Room(int number, int capacity, RoomType type) {
        this.type = type;
        this.capacity = capacity;
        this.number = number;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", capacity=" + capacity +
                ", type=" + type +
                '}';
    }
}
