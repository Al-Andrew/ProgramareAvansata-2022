package com.pearl.update;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;

import java.time.LocalDateTime;

import static com.raylib.Raylib.WindowShouldClose;

public class UpdateController implements Runnable {
    private final GameData data;

    public UpdateController(GameData data) {
        this.data = data;
    }

    @Override
    public void run() {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime target = current.plusNanos(500_000_000);
        int oldCurrentLevel = GameData.currentLevel;

        while(GameData.isRunning) {
            InputCollector.collectInput(data.inputData);
            current = LocalDateTime.now();
            GameData.isRunning = !WindowShouldClose();

            oldCurrentLevel = GameData.currentLevel;
            if(data.inputData.inputChanged || target.isBefore(current)) {
                for(EntityData entt : data.levelMaps[GameData.currentLevel].entityList) {
                    entt.brain.takeTurn(data);
                    if(oldCurrentLevel != GameData.currentLevel){
                        break;
                    }
                }
                current = LocalDateTime.now();
                target = current.plusNanos(500_000_000);
            }

            try {
                //noinspection BusyWait
                Thread.sleep(GameData.frameTimeMillis); //NOTE(Al-Andrew): This thread isn't vsynced by raylib, so we have to put it to sleep manually
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
