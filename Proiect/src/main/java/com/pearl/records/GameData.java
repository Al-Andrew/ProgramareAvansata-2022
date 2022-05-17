package com.pearl.records;


import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    public final int WINDOW_WIDTH = 1024; //NOTE(Al-Andrew): Width and height such that we have 16x9 resolution and both axis divisible by 16
    public final int WINDOW_HEIGHT = 576;
    public final String WINDOW_TITLE = "PEARL"; //TODO(Al-Andrew): generate a random sub-title at startup
    public final Raylib.Vector2 TILE_SIZE = new Jaylib.Vector2(16, 16);
    public EntityData player = new EntityData();
    public List<EntityData> entities = new ArrayList<>();
}
