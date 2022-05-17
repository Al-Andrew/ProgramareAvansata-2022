package com.pearl;

import com.pearl.graphics.Renderer;
import com.pearl.records.GameData;
import com.raylib.Jaylib;

import static com.raylib.Raylib.*;

public class Main {
    public static void main(String[] args) {
        GameData data = new GameData();
        Renderer renderer = new Renderer(data);

        InitWindow(data.WINDOW_WIDTH, data.WINDOW_HEIGHT, data.WINDOW_TITLE);
        SetTargetFPS(60);

        while (!WindowShouldClose()) {
            Vector2 delta = new Jaylib.Vector2();
            delta.x(IsKeyPressed(KEY_D)?1:0 - (IsKeyPressed(KEY_A)?1:0))
                    .y(IsKeyPressed(KEY_S)?1:0 - (IsKeyPressed(KEY_W)?1:0));

            data.player.tilePosition = Jaylib.Vector2Add(data.player.tilePosition, delta);

            renderer.drawAll();
        }
        CloseWindow();
    }
}
