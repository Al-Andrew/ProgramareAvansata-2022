package com.pearl.update;

import com.pearl.records.GameData;

import static com.raylib.Raylib.WindowShouldClose;

public class UpdateController implements Runnable {
    private final GameData data;
    private final GameInputController gameInputController;

    public UpdateController(GameData data) {
        this.data = data;
        this.gameInputController = new GameInputController(data);
    }

    @Override
    public void run() {
        while(data.isRunning) {
            data.isRunning = !WindowShouldClose();
            InputData inputData = InputCollector.collectInput();
            gameInputController.consumeGameInput(inputData);

            try {
                //noinspection BusyWait
                Thread.sleep(data.frameTimeMillis); //NOTE(Al-Andrew): This thread isn't vsynced by raylib, so we have to put it to sleep manually
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
