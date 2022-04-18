package pa;

import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private WordAssembler wordAssembler;

    public int getId() {
        return id;
    }

    private int id;


    public Player(String name) {
        this.name = name;
        this.wordAssembler = new GibberishWordAssembler();
    }

    private boolean submitWord() throws InterruptedException {
        Bag bag = game.getBag();
        List<Tile> extracted;
        extracted = game.getBag().extractTiles(7);

        if (extracted.isEmpty() || game.isLastTurn()) {
            return false;
        }
        String word = wordAssembler.assembleWords(extracted);

        game.getBoard().addWord(this, word);
        Thread.sleep(500); //NOTE(Al-Andrew): delay added just to make the game seem more real at the command line
        return true;
    }

    public String getName() {
        return name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setWordAssembler(WordAssembler wordAssembler) {
        this.wordAssembler = wordAssembler;
    }

    public void setId(int id) { this.id = id; }

    @Override
    public void run() {
        while(true) {
            try {
                game.waitForTurn(this);
                var res = submitWord();
                game.endTurn();
                if (!res) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ;
        }
    }
}
