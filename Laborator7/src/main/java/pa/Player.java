package pa;

import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private boolean running;
    public Player(String name) { this.name = name; }


    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    private boolean submitWord() throws InterruptedException {
        List<Tile> extracted = game.getBag().extractTiles(7);
        if (extracted.isEmpty()) {
            return false;
        }
        //TODO(Al-Andrew): create a word with all the extracted tiles;
        String word = "";
        for(var tile : extracted) {
            word += tile.getLetter();
        }
        game.getBoard().addWord(this, word);
        Thread.sleep(500);
        return true;
    }

    public String getName() {
        return name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while(true) {
            try {
                if (!submitWord()) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ;
        }
    }
}
