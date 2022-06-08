package com.pearl.update.brains;

import com.pearl.records.Entity;
import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.pearl.util.Mapping;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class StairUpBrain extends Brain implements BrainActionable {
    public StairUpBrain(EntityData body) {
        super(body);
    }

    @Override
    public void takeTurn(GameData data) {

    }

    @Override
    public void takeAction(EntityData actor, EntityData body, GameData data) {
        if(actor.type != Entity.PLAYER) {
            return; // For now not interested in letting monsters go between levels
        }
        EntityData player = data.levelMaps[GameData.currentLevel + 1].entityList.stream().filter((e) -> e.type == Entity.PLAYER).findFirst().orElse(null);
        EntityData connectingStairs = data.levelMaps[GameData.currentLevel + 1].entityList.stream().filter((e) -> e.type == Entity.DOWN_STAIR).findFirst().orElse(null);
        assert player != null;
        assert connectingStairs != null;
        player.tilePosition = Raylib.Vector2Add(connectingStairs.tilePosition, new Jaylib.Vector2(0, 1));
        player.stats = actor.stats;
        player.inv = actor.inv;
        data.graphicsData.cam.target(Mapping.mapToWorld(player.tilePosition));
        ++GameData.currentLevel;
        GameData.addLogEntryNormal(actor.type + " walked up the stairs.");
    }
}
