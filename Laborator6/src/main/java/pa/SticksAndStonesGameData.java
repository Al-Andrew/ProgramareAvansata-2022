package pa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.random.RandomGenerator;

public class SticksAndStonesGameData {
    private final int cols, rows;
    private StoneColor currentPlayer;
    private List<Stick> sticks;
    private List<Stone> stones;

    SticksAndStonesGameData() {
        this.cols = 10;
        this.rows = 10;
        this.sticks = new ArrayList<>();
        this.stones = new ArrayList<>();
        currentPlayer = StoneColor.values()[(int)(Math.random() * 2) + 1];
    }

    SticksAndStonesGameData(int rows, int cols) {
        this.cols = rows;
        this.rows = cols;
        this.sticks = new ArrayList<>();
        this.stones = new ArrayList<>();
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public List<Stick> getSticks() {
        return sticks;
    }

    public List<Stone> getStones() {
        return stones;
    }

    public boolean addStoneAt(int x, int y) {
        stones.add(new Stone(x, y, currentPlayer));
        //TODO(Al-Andrew): Check if the stone placement is valid
        return true;
    }

    public boolean checkWin() {
        //TODO(Al-Andrew):
        return false;
    }

    private void switchCurrentPlayer() {
        if(currentPlayer == StoneColor.RED)
            currentPlayer = StoneColor.BLUE;
        else if (currentPlayer == StoneColor.BLUE)
            currentPlayer = StoneColor.RED;
    }

    private boolean spaceUnavailable(int x, int y) {
        if(x < 0 || y < 0 || x >= cols || y >= rows )
            return false;
        for(var stick : sticks) {
            if ( stick.occupies(x, y) ) {
                return false;
            }
        }
        return true;
    }

    public void populateRandomSticks(int count) {
        List<Integer> freeNodeX = new LinkedList<>();
        List<Integer> freeNodeY = new LinkedList<>();
        freeNodeX.add(0); //Start placing the sticks in the top-left
        freeNodeY.add(0);
        RandomGenerator prng = RandomGenerator.of("Random");
        int placed = 0;

        enum Dir {
            UP, DOWN, LEFT, RIGHT
        }

        while(placed < count) {
            int currentVertex = prng.nextInt() % freeNodeX.size();
            Dir dir = Dir.values()[prng.nextInt(0 , 3)];
            System.out.println(dir);
            switch (dir) {
                case UP -> {
                    if( spaceUnavailable(freeNodeX.get(currentVertex), freeNodeY.get(currentVertex) - 1) )
                        continue;
                    else {
                        sticks.add(new Stick(freeNodeX.get(currentVertex), freeNodeY.get(currentVertex),
                                freeNodeX.get(currentVertex), freeNodeY.get(currentVertex) - 1));
                        break;
                    }
                }
                case DOWN ->  {
                    if( spaceUnavailable(freeNodeX.get(currentVertex), freeNodeY.get(currentVertex) + 1) )
                        continue;
                    else {
                        sticks.add(new Stick(freeNodeX.get(currentVertex), freeNodeY.get(currentVertex),
                                freeNodeX.get(currentVertex), freeNodeY.get(currentVertex) + 1));
                        break;
                    }
                }
                case LEFT -> {
                    if( spaceUnavailable(freeNodeX.get(currentVertex) - 1, freeNodeY.get(currentVertex)) )
                        continue;
                    else {
                        sticks.add(new Stick(freeNodeX.get(currentVertex), freeNodeY.get(currentVertex),
                                freeNodeX.get(currentVertex) - 1, freeNodeY.get(currentVertex)));
                        break;
                    }
                }
                case RIGHT -> {
                    if( spaceUnavailable(freeNodeX.get(currentVertex) + 1, freeNodeY.get(currentVertex)) )
                        continue;
                    else {
                        sticks.add(new Stick(freeNodeX.get(currentVertex), freeNodeY.get(currentVertex),
                                freeNodeX.get(currentVertex) + 1, freeNodeY.get(currentVertex)));
                        break;
                    }
                }
            }
            freeNodeX.remove(currentVertex);
            freeNodeY.remove(currentVertex);
            placed++;

        }

    }
}
