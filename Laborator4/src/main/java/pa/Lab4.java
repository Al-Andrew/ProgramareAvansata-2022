package pa;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.github.javafaker.*;


public class Lab4 {
     public static void main(String[] args) {
        Lab4 lab4 = new Lab4();


        lab4.compulsory();
        lab4.homework();
    }

    public void compulsory() {
        var intersections = IntStream.rangeClosed(1, 9)
                .mapToObj(i -> new Intersection("v" + i) )
                .collect(Collectors.toCollection(HashSet::new));

        // Here we check that set property holds
        System.out.println(intersections);
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

    public void homework() {
        var city = new City();

        int streetsToAdd = 16;
        int intersectionsToAdd = 9;
        int minStreetLength = 1;
        int maxStreetLength = 5;
        Faker faker = new Faker();

        Set<Intersection> intersectionsToBeAdded = new HashSet<>(); // We first store the intersections in a HashSet to avoid creating any duplicates
        while( intersectionsToBeAdded.size() < intersectionsToAdd ) {
            String intersectionName = faker.address().lastName();

            intersectionsToBeAdded.add(new Intersection(intersectionName));
        }
        city.addAllIntersections(intersectionsToBeAdded);

        Set<Street> streetsToBeAdded = new HashSet<>(); // Again we use a set to avoid duplicate entries
        while (streetsToBeAdded.size() < streetsToAdd){
            String streetName = faker.address().streetName();
            Random prng = new Random();
            Vector<Intersection> intersections = city.getIntersections();
            Intersection streetStart = intersections.get(prng.nextInt(0, intersections.size() - 1));
            Intersection streetEnd = intersections.get(prng.nextInt(0, intersections.size() - 1));
            if(streetStart.equals(streetEnd))
                continue; // Avoid streets that start and end at the same intersection

            int streetLength = prng.nextInt(minStreetLength,maxStreetLength);
            streetsToBeAdded.add(new Street(streetStart.getName(), streetEnd.getName(), streetName,streetLength));
        }
        city.addAllStreets(streetsToBeAdded);

        System.out.println("Randomly generated city:");
        System.out.println(city);

        int queryMinStreetLength = 3;
        int queryMinStreetsJoined = 3;

        var queryResult = city.getStreets()
                .stream()
                .filter(((Street s) -> s.getLength() >= queryMinStreetLength))
                .filter((Street s) -> s.getStreetsJoined(city.getStreets()) >= queryMinStreetsJoined)
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("The streets of the city that join at least 3 other streets:");
        System.out.println(queryResult);

        Map<Intersection, Integer> minCost = city.getIntersections()
                .stream()
                .collect(Collectors.toMap((Intersection s) -> s, (Intersection i) -> Integer.MAX_VALUE));
        Street flagStreet = new Street("flag", "flag", "FlagStreet", 0xFFFF);
        Map<Intersection, Street> bestStreet = city.getIntersections()
                .stream()
                .collect(Collectors.toMap((Intersection s) -> s, (Intersection s) -> flagStreet));

        List<Street> cabledStreets = new ArrayList<>();
        Set<Intersection> remainingIntersections = new HashSet<>(city.getIntersections());

        while( remainingIntersections.size() != 0 ) {
            Intersection current = remainingIntersections.stream().min(Comparator.comparing(minCost::get)).get();
            remainingIntersections.remove(current);
            if(!bestStreet.get(current).equals(flagStreet)) {
                cabledStreets.add(bestStreet.get(current));
            }

            List<Street> streetsConnected = city.getStreets()
                    .stream()
                    .filter((Street s) -> s.participatesInIntersection(current))
                    .collect(Collectors.toCollection(ArrayList::new));
            for(Street s : streetsConnected) {
                Intersection other = s.getOtherIntersection(current);
                if(remainingIntersections.contains(other) && s.getLength() < minCost.get(other)) {
                    minCost.replace(other, s.getLength());
                    bestStreet.replace(other, s);
                }
            }
        }
        System.out.println("Streets that should be cabled: ");
        System.out.println(cabledStreets);

    }
}
