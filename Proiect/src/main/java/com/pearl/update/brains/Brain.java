package com.pearl.update.brains;

import com.pearl.records.GameData;

public abstract class Brain {
    private GameData gameData;

    Brain(GameData gameData) { this.gameData = gameData; }


    //NOTE(Al-Anrew): the actioned is the brain that acted
    abstract void actRound(Brain actioned);
}
