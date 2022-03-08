package com.pa.laborator2;

public class LectureHall extends Room {
    private boolean hasProjector;

    public LectureHall(int number, int capacity, boolean hasProjector) {
        super(number, capacity);
        this.hasProjector = hasProjector;
    }

    public boolean isHasProjector() {
        return hasProjector;
    }

    public void setHasProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }
}
