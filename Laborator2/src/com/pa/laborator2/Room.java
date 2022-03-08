package com.pa.laborator2;

import java.util.Objects;

public abstract class Room {
    private int number;
    private int capacity;


    protected Room(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return getNumber() == room.getNumber() && getCapacity() == room.getCapacity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getCapacity());
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", capacity=" + capacity +
                '}';
    }
}