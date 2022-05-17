package com.pearl.records;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.UUID;

public class EntityData {
    public UUID uuid = UUID.randomUUID();
    public Raylib.Vector2 tilePosition = new Jaylib.Vector2(0, 0);
}
