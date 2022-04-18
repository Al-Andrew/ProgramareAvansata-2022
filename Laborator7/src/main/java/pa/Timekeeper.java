package pa;

public class Timekeeper implements Runnable {

    private Game game;

    private long startTime;
    private long maxTime;
    private final long ONE_SECOND = 1_000_000_000;

    public Timekeeper(Game game, long maxSeconds) {
        this.game = game;
        maxTime = maxSeconds;
    }

    @Override
    public void run() {
        startTime = System.nanoTime();
        while (game.isRunning()) {
            if(System.nanoTime() - startTime > maxTime * ONE_SECOND) {
                game.forceEnd();
                System.out.println("Time's up!");
                return;
            }
        }
    }
}
