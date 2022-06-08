package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.StatSheet;

public interface BrainAttackable {

    default void takeAttack(EntityData attacker, EntityData body) {
        StatSheet victimSheet = body.getStatsAfterItems();
        StatSheet attackerSheet = attacker.getStatsAfterItems();
        float damage = (10.f / (10.f + (float)(victimSheet.defence))) * (float)(attackerSheet.strength);
        victimSheet.currentHealth -= (int) damage;
    }

}
