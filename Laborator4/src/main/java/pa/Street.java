package pa;

import java.util.Set;

public class Street {
    private String name;
    private int length;
    private Intersection startIntersection; // FIXME: The names imply the relationship is directional which it isn't
    private Intersection endIntersection;


    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Street(String from, String to, String name, int length) {
        this.name = name;
        this.length = length;
        this.startIntersection = new Intersection(from);
        this.endIntersection = new Intersection(to);
    }

    @Override
    public String toString() {
        return "Street{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", startIntersection=" + startIntersection.getName() +
                ", endIntersection=" + endIntersection.getName() +
                '}';
    }
}
