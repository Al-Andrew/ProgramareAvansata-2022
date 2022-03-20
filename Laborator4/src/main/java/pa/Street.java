package pa;

import com.sun.source.tree.ReturnTree;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Street {
    private final String name;
    private final int length;
    private final Intersection startIntersection; // FIXME: The names imply the relationship is directional which it isn't
    private final Intersection endIntersection;


    public Street(String from, String to, String name, int length) {
        this.name = name;
        this.length = length;
        this.startIntersection = new Intersection(from);
        this.endIntersection = new Intersection(to);
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Street{" + "name='" + name + '\'' + ", length=" + length + ", startIntersection=" + startIntersection.getName() + ", endIntersection=" + endIntersection.getName() + '}';
    }

    public Intersection getOtherIntersection(Intersection firstIntersection) {
        return startIntersection.equals(firstIntersection)?endIntersection:startIntersection;
    }

    public boolean participatesInIntersection(Intersection intersection) {
        return intersection.equals(startIntersection) || intersection.equals(endIntersection);
    }

    public long getStreetsJoined(List<Street> streets) {
       return streets.stream().filter(((Street s) -> s.participatesInIntersection(endIntersection) || s.participatesInIntersection(endIntersection))).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return getLength() == street.getLength() && Objects.equals(getName(), street.getName()) && Objects.equals(startIntersection, street.startIntersection) && Objects.equals(endIntersection, street.endIntersection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getLength(), startIntersection, endIntersection);
    }
}
