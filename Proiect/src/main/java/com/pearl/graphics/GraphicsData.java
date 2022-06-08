package com.pearl.graphics;

import com.pearl.records.GameData;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.LoadShader;

public class GraphicsData {
    private final GameData data;
    public boolean initialized = false;
    public Raylib.Camera2D cam;
    public Raylib.Shader grayscale;

    public GraphicsData(GameData data) {
        this.data = data;
    }

    public void initialize() {
        this.initialized = true;
        this.cam = new Raylib.Camera2D();
        cam.offset(new Jaylib.Vector2(  GameData.GAME_WIDTH / 2.0f, GameData.GAME_HEIGHT / 2.0f));
        cam.rotation(0);
        cam.zoom(1.5f);
        cam.target(Raylib.Vector2Scale(data.levelMaps[GameData.currentLevel].player.tilePosition, GameData.TILE_SIZE.x()));
        this.grayscale = LoadShader(null, "assets/shaders/grayscale.fs");
    }
}
