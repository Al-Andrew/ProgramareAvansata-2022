package com.pearl.update.brains;

import com.pearl.records.*;

public interface BrainAttackable {

    default void takeAttack(EntityData attacker, EntityData body) {
        StatSheet victim = body.stats;
        StatSheet victimSheet = body.getStatsAfterItems();
        StatSheet attackerSheet = attacker.getStatsAfterItems();
        float damage = (10.f / (10.f + (float)(victimSheet.defence))) * (float)(attackerSheet.strength);
        victim.currentHealth -= (int) damage;
        String message = String.format("%s %s %s for %d damage!", attacker.type, attacker.type.attackPhrase, body.type, (int)damage);
        if(body.type == Entity.PLAYER) {
            GameData.addLogEntryBad(message);
        } else {
            GameData.addLogEntryNormal(message);
        }
    }

}
