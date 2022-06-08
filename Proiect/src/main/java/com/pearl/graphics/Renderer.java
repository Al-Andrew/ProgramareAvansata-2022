package com.pearl.graphics;

import com.pearl.records.*;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.pearl.util.Mapping.mapToWorld;
import static com.raylib.Raylib.*;

public class Renderer {
    private final GameData data;

    public Renderer(GameData data) {
        this.data = data;
        InitWindow(GameData.WINDOW_WIDTH, GameData.WINDOW_HEIGHT, GameData.WINDOW_TITLE);
        SetTargetFPS(60);
        data.texturePack.loadTextures();
        data.graphicsData.initialize();
    }

    public void drawDebugTexturePack() {
        TexturePack tp = data.texturePack;

        int cx = 0, cy = 0;
        for (var entry : tp.getTiles().entrySet()) {
                DrawTexture(entry.getValue(), cx, cy, Jaylib.WHITE);
            cx += GameData.TILE_SIZE.x();
            if (cx >= GameData.WINDOW_WIDTH) {
                cy += GameData.TILE_SIZE.y();
                cx = 0;
            }
        }
        cx = 0;
        cy = (GameData.WINDOW_HEIGHT / 2);
        for (var entry : tp.getEntities().entrySet()) {
            DrawTexture(entry.getValue(), cx, cy, Jaylib.WHITE);
            cx += GameData.TILE_SIZE.x();
            if (cx >= GameData.WINDOW_WIDTH) {
                cy += GameData.TILE_SIZE.y();
                cx = 0;
            }
        }
    }

    private void drawEntities() {
        for(EntityData ent : data.levelMaps[GameData.currentLevel].entityList) {
            if(!data.levelMaps[GameData.currentLevel].visibilityMap[ent.getTileY()][ent.getTileX()]) {
                continue;
            }
            Texture tx = data.texturePack.getEntities().get(ent.type);
            Vector2 ps =  mapToWorld(ent.tilePosition);

            DrawTextureV(tx, ps, Jaylib.WHITE);
        }
    }

    private void drawMap() {
        LevelData level = data.levelMaps[GameData.currentLevel];
        for (int y = 0; y < level.tileMap.length; ++y) {
            for (int x = 0; x < level.tileMap[y].length; ++x) {
                boolean memory = data.levelMaps[GameData.currentLevel].mapMemory[y][x];
                boolean visible = data.levelMaps[GameData.currentLevel].visibilityMap[y][x];

                if(!memory)
                    continue;

                var worldCoords = mapToWorld(x, y);
                Raylib.Texture tx = data.texturePack.getTiles().get(level.tileMap[y][x]);
                if (tx == null)
                    continue;

                if(!visible){
                    BeginShaderMode(data.graphicsData.grayscale);
                }
                DrawTextureV(tx, worldCoords, Jaylib.WHITE);
                if(!visible) {
                    EndShaderMode();
                }
            }
        }
    }

    private void drawAll() {
        EntityData player = data.levelMaps[GameData.currentLevel]
                .entityList.stream()
                .filter((e) -> e.type == Entity.PLAYER)
                .findFirst()
                .orElse(null);

        //if(player != null ) {
        //    Vector2 target = player.tilePosition;
        //    cam.target(Mapping.mapToWorld(target));
        //}

        BeginDrawing();
        ClearBackground(data.texturePack.getBgColor());

        BeginMode2D(data.graphicsData.cam);
        //drawDebugTexturePack();
        drawMap();
        drawEntities(); //NOTE(Al-Andrew): The entities should be drawn on the top-most level
        EndMode2D();

        EndDrawing();
    }

    public void run() {
        while (GameData.isRunning) {
            drawAll();
        }
    }

    public void close() {
        CloseWindow();
    }
}
