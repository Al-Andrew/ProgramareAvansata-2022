package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.pearl.records.GameOver;

public class AmuletBrain extends Brain implements BrainActionable{
    public AmuletBrain(EntityData body) {
        super(body);
    }

    @Override
    public void takeTurn(GameData data) {

    }

    @Override
    public void takeAction(EntityData actor, EntityData body, GameData data) {
        GameData.addLogEntryGood("PLAYER picked up the amulet of YANDOR");
        GameData.addLogEntryGood("PLAYER is now invincible");
        GameData.gameOver = GameOver.WIN;
    }
}
