package pa;

import java.util.*;

public class City {
    Vector<Street> streets;
    Vector<Intersection> intersections;

    public City() {
        this.streets = new Vector<>();
        this.intersections = new Vector<>();
    }

    public City(Collection<Street> streets, Collection<Intersection> intersections) {
        this.streets = new Vector<>();
        this.intersections = new Vector<>();

        this.streets.addAll(streets);
        this.intersections.addAll(intersections);
    }

    public void addStreet(Street street) {
        streets.add(street);
    }

    public void addIntersection(Intersection intersection) {
        intersections.add(intersection);
    }

    public void addAllStreets(Collection<Street> streets) {
        this.streets.addAll(streets);
    }

    public void addAllIntersections(Collection<Intersection> intersections) {
        this.intersections.addAll(intersections);
    }

    public List<Street> getStreets() {
        return streets;
    }

    public Vector<Intersection> getIntersections() {
        return intersections;
    }

    @Override
    public String toString() {
        return "City{" + System.lineSeparator() +
                "streets=" + streets + System.lineSeparator() +
                ", intersections=" + intersections + System.lineSeparator() +
                '}';
    }
}
