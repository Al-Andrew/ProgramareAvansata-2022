package pa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.random.RandomGenerator;


public class SticksAndStonesGameData {
    public int cols, rows;
    public StoneColor currentPlayer;
    public List<Stick> sticks;
    public List<Stone> stones;
    private List<Point> possibleMoves;
    public boolean firstMove;
    private MainFrame frame;

    @JsonCreator
    SticksAndStonesGameData(@JsonProperty("rows") int rows, @JsonProperty("cols") int cols,
                            @JsonProperty("sticks") List<Stick> sticks, @JsonProperty("stones") List<Stone> stones,
                           /* @JsonProperty("possibleMoves") List<Point> possibleMoves,*/
                            @JsonProperty("firstMove") boolean firstMove, @JsonProperty("currentPlayer") StoneColor currentPlayer) {
        this.cols = cols;
        this.rows = rows;
        this.sticks = sticks;
        this.stones = stones;
        this.possibleMoves = null;
        this.firstMove = firstMove;
        this.frame = null;
        this.currentPlayer = currentPlayer;
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    SticksAndStonesGameData(int rows, int cols, MainFrame frame) {
        this.cols = rows;
        this.rows = cols;
        this.sticks = new ArrayList<>();
        this.stones = new ArrayList<>();
        this.possibleMoves = new ArrayList<>();
        this.frame = frame;

        this.firstMove = true;
        currentPlayer = StoneColor.values()[(int)(Math.random() * 2)];
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

    private void managePossibleMoves(int x, int y) {
        int index = 0;
        for(var pos : possibleMoves) { //NOTE(Al-Andrew): This is stupid
            if(pos.x == x && pos.y == y) {
                break;
            }
            index++;
        }
        System.out.println("Removing: " + index);
        possibleMoves.remove(index);

        for(var d : Dir.values()) {
            if(isValidStonePlacement(moveDirX(x,d), moveDirY(y,d))) {
                System.out.println("Adding new point: " + moveDirX(x, d) + ", " + moveDirY(y, d));
                possibleMoves.add(new Point(moveDirX(x, d), moveDirY(y, d)));
            }
        }
    }

    public boolean addStoneAt(int screenX, int screenY) {
        //stones.add(new Stone(x, y, currentPlayer));

        //Check if the click was on the play-table
        Rectangle dpRect = new Rectangle(0,60,500,500); //NOTE(Al-Andrew): hard-coded because of some resizing mishmash
        if(!dpRect.contains(new Point(screenX, screenY))) {
            return false;
        }
        //Normalize the click to 0 in the drawing panel
        screenX -= dpRect.x;
        screenY -= dpRect.y;
        //Account for the padding
        screenX -= 30;
        screenY -= 30;
        //Get the indices
        int stoneXindex = screenX / frame.drawingPanel.cellWidth;
        int stoneYindex = screenY / frame.drawingPanel.cellHeight;
        //Verify if placement is valid
        //If it's the first move just check that it's in within the sticks
        boolean ok = false;
        if(firstMove) {
            for(var st : sticks) {
                if(st.occupies(stoneXindex, stoneYindex)) {
                    ok = true;
                    break;
                }
            }
            if(ok) {
                firstMove = false;
                possibleMoves.add(new Point(stoneXindex, stoneYindex));
            }
        }
        else {
            for(var pos : possibleMoves) {
                if(pos.x == stoneXindex && pos.y == stoneYindex) {
                    ok = true;
                    break;
                }
            }
        }
        if(!ok)
            return false;

        stones.add(new Stone(stoneXindex, stoneYindex, currentPlayer));
        managePossibleMoves(stoneXindex, stoneYindex);
        System.out.println(possibleMoves);
        switchCurrentPlayer();
        frame.drawingPanel.updateUI();
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

    enum Dir {
        UP, DOWN, LEFT, RIGHT
    }

    private int moveDirX(int current, Dir dir) {
        switch (dir){
            case RIGHT -> {
                return current + 1;
            }
            case LEFT -> {
                return current - 1;
            }
            case UP, DOWN -> {
                return current;
            }
        }
        return current; //should be unreachable
    }

    private int moveDirY(int current, Dir dir) {
        switch (dir){
            case RIGHT,LEFT -> {
                return current;
            }
            case UP -> {
                return current - 1;
            }
            case DOWN -> {
                return current + 1;
            }
        }
        return current; //should be unreachable
    }

    private boolean isValidStickEnd(int x, int y) {
        if(x < 0 || y < 0 || x >= getRows() || y >= getCols() )
            return false;

        for(var stick : sticks) {
            if( stick.occupies(x,y) )
                return false;
        }

        return true;
    }

    private boolean isValidStonePlacement(int x, int y) {
        if(x < 0 || y < 0 || x >= getRows() || y >= getCols() )
            return false;
        for(var st : stones) {
            if(st.x == x && st.y == y)
                return false;
        }
        for(var stick : sticks) {
            if( stick.occupies(x,y) )
                return true;
        }
        return false;
    }

    private boolean shouldRemoveVertex(int x, int y) {
        for(var dir : Dir.values()) {
            if(isValidStickEnd(moveDirX(x, dir), moveDirY(y, dir))){
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

        while(placed < count) {
            int currentVertex = prng.nextInt(0 , freeNodeX.size());
            int currentX = freeNodeX.get(currentVertex);
            int currentY = freeNodeY.get(currentVertex);
            Dir dir = Dir.values()[prng.nextInt(0 , 4)];
            //Compute the new position
            int newX = moveDirX(currentX, dir);
            int newY = moveDirY(currentY, dir);
            //Check if position is valid
            boolean isValid = isValidStickEnd(newX, newY);
            //if not valid skip this trial
            if(!isValid)
                continue;
            //if valid place and manage free nodes
            var newStick = new Stick(currentX, currentY, newX, newY);
            sticks.add(newStick);
            if(shouldRemoveVertex(currentX, currentY)) {
                freeNodeX.remove(currentVertex);
                freeNodeY.remove(currentVertex);
            }
            freeNodeX.add(newX);
            freeNodeY.add(newY);
            placed = placed + 1;
            if(freeNodeX.isEmpty())
                break;
        }

    }
}
