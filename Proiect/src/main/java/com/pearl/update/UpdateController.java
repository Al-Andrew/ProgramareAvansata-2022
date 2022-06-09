package com.pearl.update;

import com.pearl.records.Entity;
import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.pearl.records.GameOver;
import com.pearl.util.Mapping;
import com.raylib.Jaylib;
import com.raylib.Raylib;

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
            GameData.isRunning = !WindowShouldClose() && GameData.isRunning;

            if(GameData.gameOver != GameOver.NONE)
                continue;

            current = LocalDateTime.now();
            oldCurrentLevel = GameData.currentLevel; //We need to stop letting actions go trough if the level changed
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
            data.levelMaps[GameData.currentLevel].updateVisibility();

            data.levelMaps[GameData.currentLevel]
                    .entityList
                    .removeIf(entt -> {
                        boolean shouldRemove = entt.getStatsAfterItems().currentHealth <= 0;
                        if(entt.type == Entity.PLAYER && shouldRemove)
                            GameData.gameOver = GameOver.LOSE;
                        return shouldRemove;
                    });

            updateCameraSmooth();

            try {
                //noinspection BusyWait
                Thread.sleep(GameData.frameTimeMillis); //NOTE(Al-Andrew): This thread isn't vsynced by raylib, so we have to put it to sleep manually
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCameraSmooth() {
        float minSpeed = 30;
        float minEffectLength = 6;
        float fractionSpeed = 0.8f;

        data.graphicsData.cam.offset( new Jaylib.Vector2(GameData.GAME_WIDTH/2.0f,
                GameData.GAME_HEIGHT/2.0f ));
        Raylib.Vector2 playerWorldPos = Mapping.mapToWorld(data.levelMaps[GameData.currentLevel].player.tilePosition);
        Raylib.Vector2 diff = Raylib.Vector2Subtract(playerWorldPos, data.graphicsData.cam.target());
        float length = Raylib.Vector2Length(diff);

        if (length > minEffectLength)
        {
            float speed = Math.max(fractionSpeed*length, minSpeed);
            data.graphicsData.cam.target(Raylib.Vector2Add(data.graphicsData.cam.target(),
                    Raylib.Vector2Scale(diff, (speed*GameData.frameTimeMillis/1000)/length)));
        }
        float newZoom = data.graphicsData.cam.zoom() + data.inputData.mouseWheelDelta;
        newZoom = Math.max(Math.abs(newZoom), 0.5f);
        data.graphicsData.cam.zoom(newZoom);
    }
}
