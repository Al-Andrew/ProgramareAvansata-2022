package pa;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lab4 {
     public static void main(String[] args) {
        Lab4 lab4 = new Lab4();


        lab4.compulsory();
    }

    public void compulsory() {
        var intersections = IntStream.rangeClosed(0, 9)
                .mapToObj(i -> new Intersection("v" + i) )
                .collect(Collectors.toCollection(HashSet::new));

        // Here we check that set property holds
        int oldSetSize = intersections.size();
        intersections.add(new Intersection("v2")); // this should already be in the set
        if(oldSetSize == intersections.size())
            System.out.println("Set property holds!");

        var streets = Stream.of(
                new Street("v1", "v2", "s1", 1),
                new Street("v2", "v3", "s2", 1),
                new Street("v1", "v3", "s3", 2),
                new Street("v2", "v4", "s4", 1),
                new Street("v3", "v4", "s5", 1),
                new Street("v1", "v5", "s6", 1),
                new Street("v3", "v5", "s6", 1),
                new Street("v4", "v6", "s7", 2),
                new Street("v5", "v6", "s8", 2),
                new Street("v5", "v7", "s9", 3),
                new Street("v1", "v9", "s10", 3),
                new Street("v6", "v9", "s11", 2),
                new Street("v6", "v7", "s12", 1),
                new Street("v6", "v8", "s13", 2),
                new Street("v9", "v8", "s13", 2),
                new Street("v7", "v8", "s14", 2)
        ).collect(Collectors.toCollection(LinkedList::new));

        System.out.println("Streets list before sort: ");
        System.out.println(streets);
        streets.sort(Comparator.comparingInt(Street::getLength));
        System.out.println("Streets list after sort: ");
        System.out.println(streets);
    }
}
