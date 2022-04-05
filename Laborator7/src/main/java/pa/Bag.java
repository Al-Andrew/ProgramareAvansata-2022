package pa;

import java.util.*;

public class Bag {
    private final List<Tile> tiles;
    Map<Character, Integer> letterCounts = new HashMap<Character, Integer>() {{
        put('a', 9);
        put('b', 2);
        put('c', 2);
        put('d', 4);
        put('e', 12);
        put('f', 2);
        put('g', 3);
        put('h', 2);
        put('i', 9);
        put('j', 1);
        put('k', 1);
        put('l', 4);
        put('m', 2);
        put('n', 6);
        put('o', 8);
        put('p', 2);
        put('q', 1);
        put('r', 6);
        put('s', 4);
        put('t', 6);
        put('u', 4);
        put('v', 2);
        put('w', 2);
        put('x', 1);
        put('y', 2);
        put('z', 1);}};
    Map<Character, Integer> letterPoints = new HashMap<Character, Integer>() {{
        put('a', 1);
        put('b', 3);
        put('c', 3);
        put('d', 2);
        put('e', 1);
        put('f', 4);
        put('g', 2);
        put('h', 4);
        put('i', 1);
        put('j', 8);
        put('k', 5);
        put('l', 1);
        put('m', 3);
        put('n', 1);
        put('o', 1);
        put('p', 3);
        put('q', 10);
        put('r', 1);
        put('s', 1);
        put('t', 1);
        put('u', 1);
        put('v', 4);
        put('w', 4);
        put('x', 8);
        put('y', 4);
        put('z', 10);}};

    public Bag() {
        this.tiles = new ArrayList<>();
            for(char curr = 'a'; curr < 'z' ; curr++) {
                for(int i = 0; i < letterCounts.get(curr) ; i++) {
                    this.tiles.add(new Tile(curr, letterPoints.get(curr)));
                }
            }
            Collections.shuffle(tiles); // Put the tiles in a random order in the bag
    }

    public synchronized List<Tile> extractTiles(int howMany) {
        List<Tile> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            if (tiles.isEmpty()) {
                break;
            }
            extracted.add(tiles.get(tiles.size() - 1));
            tiles.remove(tiles.size() - 1);
        }
        return extracted;
    }
}
