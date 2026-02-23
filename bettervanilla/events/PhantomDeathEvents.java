package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Vex;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * Feature 9: When a phantom dies, spawn a Vex (or configured mob).
 * Feature 10: When wither dies, spawn 3 black skeletons with equipment.
 */
public class PhantomDeathEvents {

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) return;

        // Phantom death
        if (BetterVanillaConfig.PHANTOM_DEATH_SPAWN_ENABLED.get() && event.getEntity() instanceof Phantom phantom) {
            String mobId = BetterVanillaConfig.PHANTOM_DEATH_MOB.get();
            ResourceLocation rl = ResourceLocation.tryParse(mobId);

            if (rl != null && BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
                EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(rl);
                net.minecraft.world.entity.Entity spawned = entityType.create(serverLevel);
                if (spawned != null) {
                    spawned.moveTo(phantom.getX(), phantom.getY(), phantom.getZ(), phantom.getYRot(), 0);
                    serverLevel.addFreshEntity(spawned);
                }
            } else {
                // Fallback: spawn vex
                Vex vex = EntityType.VEX.create(serverLevel);
                if (vex != null) {
                    vex.moveTo(phantom.getX(), phantom.getY(), phantom.getZ());
                    serverLevel.addFreshEntity(vex);
                }
            }
        }
    }
}
