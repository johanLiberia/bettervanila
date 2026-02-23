package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * Feature 6: Villagers fight back when attacked by player.
 */
public class VillagerCombatEvents {

    @SubscribeEvent
    public void onVillagerHurt(LivingDamageEvent.Pre event) {
        if (!BetterVanillaConfig.VILLAGER_FIGHT_BACK_ENABLED.get()) return;
        if (!(event.getEntity() instanceof Villager villager)) return;
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (!(villager.level() instanceof ServerLevel)) return;

        // Make villager target the player
        villager.setTarget(player);

        // Add temporary melee attack goal if not present
        // Villagers don't normally attack, so we deal damage directly
        villager.level().getServer().execute(() -> {
            // After being hit, villager retaliates with a weak attack (2 damage)
            player.hurt(villager.level().damageSources().mobAttack(villager), 2.0f);
        });
    }
}
