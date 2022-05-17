package com.pearl.graphics;

import com.pearl.records.GameData;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.RED;
import static com.raylib.Raylib.*;

public class Renderer {
    private GameData data;

    public Renderer(GameData data) {
        this.data = data;
    }

    public void drawAll() {
        BeginDrawing();
        ClearBackground(new Jaylib.Color(0, 0, 0, 0));

        var playerWorldPosition = Raylib.Vector2Scale(data.player.tilePosition, 16);

        DrawRectangleV(playerWorldPosition,
                data.TILE_SIZE,
                RED);


        EndDrawing();
    }

}
