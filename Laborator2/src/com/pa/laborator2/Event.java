package com.pa.laborator2;

public class Event {
    private String name;
    private int size;
    /*
     * Am presupus ca toate evnimentele au loc in aceeasi zi. Am impus in settere:
     *  - start si end >= 0
     *  = start so emd <= 24
     *  - start < end
     * */
    private int start;
    private int end;

    public Event(String name, int size, int start, int end) {
        this.name = name;
        this.size = size;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) return;
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size < 0) return;
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        if (start < 0 || start > 24) return;
        if (start >= this.end) return;
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        if (end < 0 || end > 24) return;
        if (end < this.start) return;
        this.end = end;
    }

    public void setStartEnd(int start, int end) {
        if (start < 0 || end < 0) return;
        if (start > 24 || end > 24) return;
        if (start > end) return;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Event{" + "name='" + name + '\'' + ", size=" + size + ", start=" + start + ", end=" + end + '}';
    }
}
