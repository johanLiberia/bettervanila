package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Feature 13: In dark mines, if no torches nearby, a "Torch Zombie" may appear near the player.
 * - Sets player on fire on hit
 * - Drops 16 torches on death
 */
public class DarkMineEvents {

    private static final Map<UUID, Integer> cooldownMap = new HashMap<>();
    private static final int SPAWN_COOLDOWN_TICKS = 1200; // 60 seconds
    private static final int LIGHT_LEVEL_THRESHOLD = 4;
    private static final String TORCH_ZOMBIE_TAG = "bettervanilla_torch_zombie";

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        if (!BetterVanillaConfig.DARK_MINE_ZOMBIE_ENABLED.get()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % 100 != 0) return; // Check every 5 seconds

        ServerLevel level = player.serverLevel();

        // Only in overworld underground
        if (!level.dimension().equals(Level.OVERWORLD)) return;
        if (player.getY() > 50) return; // Must be underground

        BlockPos pos = player.blockPosition();
        int lightLevel = level.getMaxLocalRawBrightness(pos);

        if (lightLevel > LIGHT_LEVEL_THRESHOLD) return; // Too bright

        // Check for torches nearby
        boolean hasTorchNearby = false;
        for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-8, -2, -8), pos.offset(8, 2, 8))) {
            if (level.getBlockState(checkPos).is(net.minecraft.world.level.block.Blocks.TORCH) ||
                level.getBlockState(checkPos).is(net.minecraft.world.level.block.Blocks.WALL_TORCH)) {
                hasTorchNearby = true;
                break;
            }
        }

        if (hasTorchNearby) return;

        // Check cooldown
        UUID uid = player.getUUID();
        int cooldown = cooldownMap.getOrDefault(uid, 0);
        if (cooldown > player.tickCount) return;

        // Spawn torch zombie nearby
        Zombie zombie = EntityType.ZOMBIE.create(level);
        if (zombie == null) return;

        double angle = level.random.nextDouble() * Math.PI * 2;
        double dist = 10 + level.random.nextDouble() * 6;
        double spawnX = player.getX() + Math.cos(angle) * dist;
        double spawnZ = player.getZ() + Math.sin(angle) * dist;

        zombie.moveTo(spawnX, player.getY(), spawnZ, level.random.nextFloat() * 360, 0);

        // Equipment
        zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
        zombie.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, new ItemStack(Items.TORCH, 16));

        // Tag as torch zombie
        zombie.getPersistentData().putBoolean(TORCH_ZOMBIE_TAG, true);
        zombie.setPersistenceRequired();
        zombie.setTarget(player);

        level.addFreshEntity(zombie);
        cooldownMap.put(uid, player.tickCount + SPAWN_COOLDOWN_TICKS);
    }

    @SubscribeEvent
    public void onTorchZombieHit(LivingDamageEvent.Pre event) {
        if (!BetterVanillaConfig.DARK_MINE_ZOMBIE_ENABLED.get()) return;
        if (!(event.getSource().getEntity() instanceof Zombie zombie)) return;
        if (!zombie.getPersistentData().getBoolean(TORCH_ZOMBIE_TAG)) return;

        // Set target on fire
        if (event.getEntity() instanceof net.minecraft.world.entity.LivingEntity target) {
            target.setSecondsOnFire(5);
        }
    }

    @SubscribeEvent
    public void onTorchZombieDeath(LivingDeathEvent event) {
        if (!BetterVanillaConfig.DARK_MINE_ZOMBIE_ENABLED.get()) return;
        if (!(event.getEntity() instanceof Zombie zombie)) return;
        if (!zombie.getPersistentData().getBoolean(TORCH_ZOMBIE_TAG)) return;
        if (!(zombie.level() instanceof ServerLevel serverLevel)) return;

        // Drop 16 torches
        net.minecraft.world.entity.item.ItemEntity itemEntity = new net.minecraft.world.entity.item.ItemEntity(
                serverLevel, zombie.getX(), zombie.getY(), zombie.getZ(),
                new ItemStack(Items.TORCH, 16));
        serverLevel.addFreshEntity(itemEntity);
    }
}
