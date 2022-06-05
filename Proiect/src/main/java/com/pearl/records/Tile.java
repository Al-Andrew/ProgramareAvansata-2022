package com.pearl.records;

public enum Tile {
    BACKGROUND,
    DUNGEON_FLOOR,
    DUNGEON_BRICK_WALL(true),
    DUNGEON_SLATE_WALL,
    CAVE_FLOOR,
    CAVE_WALL,
    BOULDER,
    STAIRWAY_UP,
    STAIRWAY_DOWN,
    ;

    boolean solid;

    Tile() {
        this.solid = false;
    }

    Tile(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return solid;
    }
}
