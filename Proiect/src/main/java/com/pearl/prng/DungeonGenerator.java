package com.pearl.prng;

import com.pearl.records.Entity;
import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.pearl.records.Tile;
import com.pearl.util.MathUtil;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.*;

public class DungeonGenerator implements TerrainGenerator {
    @Override
    public void generate(Tile[][] tiles, Vector<EntityData> entityList, int depth) {
        Random prng = new Random();
        //We're going to have rooms
        class Room {
            private static final int MAX_WIDTH = 15;
            private static final int MIN_WIDTH = 7;
            private static final int MAX_HEIGHT = 15;
            private static final int MIN_HEIGHT = 7;
            public final int x;
            public final int y;
            public final int w;
            public final int h;

            public Room(Random rng, int maxX, int maxY) {
                this.w = rng.nextInt(MIN_WIDTH, MAX_WIDTH);
                this.h = rng.nextInt(MIN_HEIGHT, MAX_HEIGHT);
                this.x = rng.nextInt(0, maxX - this.w - 1);
                this.y = rng.nextInt(0, maxY - this.h - 1);
            }

            public Raylib.Vector2 getCenter() {
                return new Jaylib.Vector2(this.x + this.w/2 , this.y + this.h/2);
            }

            public Raylib.Vector2 getRandomCorner() {
                int roll = prng.nextInt(0, 3);
                return switch (roll) {
                    case 1 -> new Jaylib.Vector2(this.x + 1, this.y + this.h - 2);
                    case 2 -> new Jaylib.Vector2(this.x + this.w - 2, this.y + 1);
                    case 3 -> new Jaylib.Vector2(this.x + this.w - 2, this.y + this.h - 2);
                    default -> new Jaylib.Vector2(this.x + 1, this.y + 1);
                };
            }

            public void updateEmptySpaceBitmap(boolean[][] target) {
                for (int i = y + 1; i < y + h - 1; ++i) {
                    for (int j = x + 1; j < x + w - 1; ++j) {
                        target[i][j] = true;
                    }
                }
            }

            public boolean intersectsRoom(Room other) {
                return this.x <= other.x + other.w &&
                        this.x + this.w >= other.x &&
                        this.y <= other.y + other.h &&
                        this.y + this.h >= other.y;
            }

        }

        class Corridor {
            final Room from;
            final Room to;

            Corridor(Room r1, Room r2){
                this.from = r1;
                this.to = r2;
            }

            public void updateEmptySpaceBitmap(boolean[][] target) {
                //We will link the center-points of the rooms
                int x1 = from.x + (from.w / 2);
                int y1 = from.y + (from.h / 2);
                int x2 = to.x + (to.w / 2);
                int y2 = to.y + (to.h / 2);

                for(int i = Math.min(y1, y2); i <= Math.max(y1, y2); ++i) {
                    target[i][x2] = true;
                }
                for(int i = Math.min(x1, x2); i <= Math.max(x1, x2); ++i) {
                    target[y1][i] = true;
                }
            }

        }

        //Generate the rooms
        final int MAX_ROOMS = 100;
        List<Room> rooms = new ArrayList<>();
        boolean[][] emptySpace = new boolean[tiles.length][tiles[0].length];
        for(boolean[] line : emptySpace) {
            Arrays.fill(line, false);
        }
        for(int i = 0; i < MAX_ROOMS; ++i) {
            Room newRoom = new Room(prng,  tiles[0].length - 1, tiles.length - 1);
            if(rooms.stream().noneMatch((Room r) -> r.intersectsRoom(newRoom))) {
                rooms.add(newRoom);
                newRoom.updateEmptySpaceBitmap(emptySpace);
                if(rooms.size() >= 2) {
                    Room prev = rooms.get(rooms.size() - 2);

                    Corridor cr;
                    cr = new Corridor(newRoom, prev);
                    cr.updateEmptySpaceBitmap(emptySpace);
                }
            }
        }
        //Populate the actual tiles
        for(int y = 1; y < emptySpace.length - 1 ; ++y){
            for(int x = 1; x < emptySpace[0].length - 1 ; ++x) {
                if(emptySpace[y][x]) {
                    for(int i = x - 1; i < x + 2; ++i) {
                        for(int j = y - 1; j < y + 2; ++j) {
                            if(!emptySpace[j][i]){
                                tiles[j][i] = Tile.DUNGEON_BRICK_WALL;
                            }
                        }
                    }
                    tiles[y][x] = Tile.DUNGEON_FLOOR;
                }
            }
        }
        //Now for some entities
        Room firstRoom = rooms.get(0);
        Room lastRoom = rooms.get(rooms.size() - 1);

        if(depth == 0) {
            entityList.add(EntityData.withPosition((int) firstRoom.getCenter().x(), (int) firstRoom.getCenter().y(), Entity.PLAYER));
        } else {
            entityList.add(EntityData.withPosition((int) firstRoom.getCenter().x(), (int) firstRoom.getCenter().y(), Entity.DOWN_STAIR));
            entityList.add(EntityData.withPosition((int) firstRoom.getCenter().x(), (int) firstRoom.getCenter().y() + 1, Entity.PLAYER));
        }
        if(depth != GameData.LEVEL_COUNT - 1)
            entityList.add(EntityData.withPosition((int) lastRoom.getCenter().x(), (int) lastRoom.getCenter().y(), Entity.UP_STAIR));
        else
            entityList.add(EntityData.withPosition((int) lastRoom.getCenter().x(), (int) lastRoom.getCenter().y(), Entity.AMULET));

        rooms.stream()
                .skip(1)
                .forEach((Room r) -> {
                    if(r == lastRoom) { //We have something else for the last room.
                        return;
                    }
                    //Weighted chance for encounters:
                    // 20% - no encounter
                    // 60% - 1 goblin
                    // 15% - 2 goblins
                    // 5% - 3 goblins
                    int tableRoll = prng.nextInt(0, 100);
                    if (MathUtil.betweenInclusive(tableRoll, 20, 79)) {
                        entityList.add(EntityData.withPosition((int) r.getCenter().x(), (int) r.getCenter().y(), Entity.RAT));
                    } else if (MathUtil.betweenInclusive(tableRoll, 80, 94)) {
                        entityList.add(EntityData.withPosition((int) r.getCenter().x(), (int) r.getCenter().y() + 2, Entity.RAT));
                        entityList.add(EntityData.withPosition((int) r.getCenter().x(), (int) r.getCenter().y() - 2, Entity.RAT));
                    } else if (MathUtil.betweenInclusive(tableRoll, 95, 99)) {
                        entityList.add(EntityData.withPosition((int) r.getCenter().x() + 2, (int) r.getCenter().y() + 2, Entity.RAT));
                        entityList.add(EntityData.withPosition((int) r.getCenter().x(), (int) r.getCenter().y(), Entity.RAT));
                        entityList.add(EntityData.withPosition((int) r.getCenter().x() - 2, (int) r.getCenter().y() - 2, Entity.RAT));
                    }
                    //Roll again. this time for treasure 25% chance of having treasure in a room
                    tableRoll = prng.nextInt(0, 100);
                    if(MathUtil.betweenInclusive(tableRoll, 0, 24)) {
                        //Now roll for what treasure. random and equally split
                        tableRoll = prng.nextInt(0, 4);
                        Raylib.Vector2 pos = r.getRandomCorner();
                        switch (tableRoll) {
                            case 0 -> {
                                entityList.add(EntityData.withPosition((int) pos.x(), (int) pos.y(), Entity.HEALTH_UP));
                            }
                            case 1 -> {
                                entityList.add(EntityData.withPosition((int) pos.x(), (int) pos.y(), Entity.FULL_HEALTH));
                            }
                            case 2 -> {
                                entityList.add(EntityData.withPosition((int) pos.x(), (int) pos.y(), Entity.STRENGTH_UP));
                            }
                            case 3 -> {
                                entityList.add(EntityData.withPosition((int) pos.x(), (int) pos.y(), Entity.DEFENCE_UP));
                            }
                        }
                    }

                });

    }
}
