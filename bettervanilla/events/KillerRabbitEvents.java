package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.world.entity.animal.Rabbit;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingSpawnEvent;

/**
 * Feature 12: Killer rabbits replace normal rabbits with configured chance.
 */
public class KillerRabbitEvents {

    @SubscribeEvent
    public void onRabbitSpawn(LivingSpawnEvent.SpecialSpawn event) {
        if (!BetterVanillaConfig.KILLER_RABBIT_REPLACE_ENABLED.get()) return;
        if (!(event.getEntity() instanceof Rabbit rabbit)) return;

        double chance = BetterVanillaConfig.KILLER_RABBIT_SPAWN_CHANCE.get() / 100.0;

        if (event.getLevel().getRandom().nextDouble() < chance) {
            rabbit.setRabbitType(Rabbit.Variant.THE_KILLER_BUNNY);
        }
    }
}
