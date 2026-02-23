package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.raider.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

/**
 * Feature 4: Raid mechanics improvements:
 * - Raiders kill ALL passive mobs (not just villagers/golems)
 * - Pillager death → skeletal archer with shield and axe
 * - Ravager death → explosion (no block damage)  
 * - Evoker death → spawn vexes
 */
public class RaidEvents {

    @SubscribeEvent
    public void onEntityTick(EntityTickEvent.Post event) {
        if (!BetterVanillaConfig.RAID_KILL_PASSIVE_MOBS_ENABLED.get()) return;
        if (!(event.getEntity() instanceof AbstractRaider raider)) return;
        if (!(raider.level() instanceof ServerLevel serverLevel)) return;
        if (raider.tickCount % 40 != 0) return; // Check every 2 seconds

        // Check if raider is in a raid
        if (!raider.hasActiveRaid()) return;

        // Find nearby passive mobs and attack them if no current target
        if (raider.getTarget() != null) return;

        List<Animal> nearbyAnimals = serverLevel.getEntitiesOfClass(Animal.class,
                raider.getBoundingBox().inflate(16), animal -> !animal.isBaby());

        if (!nearbyAnimals.isEmpty()) {
            Animal target = nearbyAnimals.get(serverLevel.random.nextInt(nearbyAnimals.size()));
            raider.setTarget(target);
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) return;

        Entity dying = event.getEntity();

        // Pillager death → skeleton with shield and axe
        if (BetterVanillaConfig.RAID_KILL_PASSIVE_MOBS_ENABLED.get() && dying instanceof Pillager) {
            if (dying instanceof Pillager pillager && pillager.hasActiveRaid()) {
                spawnArmedSkeleton(serverLevel, dying);
            }
        }

        // Ravager death → non-block explosion
        if (BetterVanillaConfig.RAID_KILL_PASSIVE_MOBS_ENABLED.get() && dying instanceof Ravager) {
            serverLevel.explode(null, dying.getX(), dying.getY(), dying.getZ(),
                    3.0f, Level.ExplosionInteraction.NONE); // No block damage
        }

        // Evoker death → spawn vexes
        if (BetterVanillaConfig.RAID_KILL_PASSIVE_MOBS_ENABLED.get() && dying instanceof Evoker) {
            int vexCount = 2 + serverLevel.random.nextInt(3); // 2-4 vexes
            for (int i = 0; i < vexCount; i++) {
                Vex vex = EntityType.VEX.create(serverLevel);
                if (vex != null) {
                    vex.moveTo(dying.getX() + serverLevel.random.nextGaussian(),
                            dying.getY() + 1,
                            dying.getZ() + serverLevel.random.nextGaussian(),
                            dying.getYRot(), 0);
                    serverLevel.addFreshEntity(vex);
                }
            }
        }
    }

    private void spawnArmedSkeleton(ServerLevel level, Entity at) {
        Skeleton skeleton = EntityType.SKELETON.create(level);
        if (skeleton == null) return;

        skeleton.moveTo(at.getX(), at.getY(), at.getZ(), at.getYRot(), 0);

        // Give skeleton a shield and iron axe
        skeleton.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,
                new ItemStack(Items.IRON_AXE));
        skeleton.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND,
                new ItemStack(Items.SHIELD));

        level.addFreshEntity(skeleton);
    }
}
