package com.pa.laborator2;

public class ComputerLab extends Room {
    private OperatingSystem labOs;

    public ComputerLab(int number, int capacity, OperatingSystem labOs) {
        super(number, capacity);
        this.labOs = labOs;
    }

    public OperatingSystem getLabOs() {
        return labOs;
    }

    public void setLabOs(OperatingSystem labOs) {
        this.labOs = labOs;
    }

}
