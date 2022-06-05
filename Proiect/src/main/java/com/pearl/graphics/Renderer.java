package com.pearl.graphics;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.pearl.records.LevelData;
import com.pearl.records.TexturePack;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.pearl.util.Mapping.mapToWorld;
import static com.raylib.Raylib.*;

public class Renderer {
    private final GameData data;

    public Renderer(GameData data) {
        this.data = data;
        InitWindow(data.WINDOW_WIDTH, data.WINDOW_HEIGHT, data.WINDOW_TITLE);
        SetTargetFPS(60);
        data.texturePack.loadTextures();
    }

    public void drawDebugTexturePack() {
        TexturePack tp = data.texturePack;

        int cx = 0, cy = 0;
        for (var entry : tp.getTiles().entrySet()) {
                DrawTexture(entry.getValue(), cx, cy, Jaylib.WHITE);
            cx += data.TILE_SIZE.x();
            if (cx >= data.WINDOW_WIDTH) {
                cy += data.TILE_SIZE.y();
                cx = 0;
            }
        }
        cx = 0;
        cy = (data.WINDOW_HEIGHT / 2);
        for (var entry : tp.getEntities().entrySet()) {
            DrawTexture(entry.getValue(), cx, cy, Jaylib.WHITE);
            cx += data.TILE_SIZE.x();
            if (cx >= data.WINDOW_WIDTH) {
                cy += data.TILE_SIZE.y();
                cx = 0;
            }
        }
    }

    private void drawEntities() {
        for(EntityData ent : data.levelMaps[data.currentLevel].entityList) {
            Texture tx = data.texturePack.getEntities().get(ent.type);
            Vector2 ps =  mapToWorld(ent.tilePosition);

            DrawTextureV(tx, ps, Jaylib.WHITE);
        }
    }

    private void drawMap() {
        LevelData level = data.levelMaps[data.currentLevel];
        for (int y = 0; y < level.tileMap.length; ++y) {
            for (int x = 0; x < level.tileMap[y].length; ++x) {
                var worldCoords = mapToWorld(x, y);
                Raylib.Texture tx = data.texturePack.getTiles().get(level.tileMap[y][x]);
                if(tx == null ) {
                    Raylib.DrawRectangle((int) worldCoords.x(),
                            (int) worldCoords.y(),
                            (int) data.TILE_SIZE.x(),
                            (int) data.TILE_SIZE.y(), data.texturePack.getBgColor());
                }
                else
                    DrawTextureV(tx, worldCoords, Jaylib.WHITE);
            }
        }
    }

    private void drawAll() {
        BeginDrawing();
        ClearBackground(new Jaylib.Color(0, 0, 0, 0));

        //drawDebugTexturePack();
        drawMap();
        drawEntities(); //NOTE(Al-Andrew): The entities should be drawn on the top-most level

        EndDrawing();
    }

    public void run() {
        while (data.isRunning) {
            drawAll();
        }
    }

    public void close() {
        CloseWindow();
    }
}
