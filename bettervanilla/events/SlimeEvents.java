package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingSpawnEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

/**
 * Feature 5: Slime mechanics:
 * - Slimes spawn in swamps during rain
 * - Small slimes merge into big slimes
 * - Slimes/Magma cubes have chance to drop special loot
 * - Magma cubes spawn near lava in overworld
 */
public class SlimeEvents {

    @SubscribeEvent
    public void onSlimeTick(EntityTickEvent.Post event) {
        if (!BetterVanillaConfig.SLIME_MERGE_ENABLED.get()) return;
        if (!(event.getEntity() instanceof Slime slime)) return;
        if (!(slime.level() instanceof ServerLevel serverLevel)) return;
        if (slime.getSize() != 1) return; // Only tiny slimes
        if (slime.tickCount % 40 != 0) return;

        // Look for nearby tiny slimes to merge
        List<Slime> nearbySlimes = serverLevel.getEntitiesOfClass(Slime.class,
                slime.getBoundingBox().inflate(3), s -> s != slime && s.getSize() == 1);

        if (nearbySlimes.size() >= 3) {
            // Merge: remove nearby slimes and spawn a big slime
            nearbySlimes.subList(0, Math.min(3, nearbySlimes.size())).forEach(s -> s.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED));
            slime.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);

            Slime bigSlime = net.minecraft.world.entity.EntityType.SLIME.create(serverLevel);
            if (bigSlime != null) {
                bigSlime.setSize(2, true); // Medium size
                bigSlime.moveTo(slime.getX(), slime.getY(), slime.getZ());
                serverLevel.addFreshEntity(bigSlime);
            }
        }
    }

    @SubscribeEvent
    public void onSlimeDeath(LivingDeathEvent event) {
        if (!BetterVanillaConfig.SLIME_LOOT_ENABLED.get()) return;
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) return;

        boolean isSlime = event.getEntity() instanceof Slime;
        boolean isMagmaCube = event.getEntity() instanceof MagmaCube;

        if (!isSlime && !isMagmaCube) return;

        double chance = BetterVanillaConfig.SLIME_LOOT_CHANCE.get() / 100.0;
        if (serverLevel.random.nextDouble() > chance) return;

        // Random special loot
        ItemStack loot = getRandomSlimeLoot(serverLevel);
        serverLevel.addItem(new net.minecraft.world.entity.item.ItemEntity(
                serverLevel,
                event.getEntity().getX(),
                event.getEntity().getY(),
                event.getEntity().getZ(),
                loot));
    }

    @SubscribeEvent
    public void onMobSpawnCheck(LivingSpawnEvent.CheckSpawn event) {
        // Magma cube near lava in overworld
        if (!BetterVanillaConfig.MAGMA_CUBE_OVERWORLD_SPAWN_ENABLED.get()) return;
        if (!(event.getEntity() instanceof MagmaCube magmaCube)) return;
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!serverLevel.dimension().equals(Level.OVERWORLD)) return;

        // Allow spawning near lava in overworld
        BlockPos pos = magmaCube.blockPosition().below();
        if (serverLevel.getBlockState(pos).is(net.minecraft.world.level.block.Blocks.LAVA) ||
            serverLevel.getBlockState(pos).is(net.minecraft.world.level.block.Blocks.MAGMA_BLOCK)) {
            event.setResult(net.neoforged.bus.api.Event.Result.ALLOW);
        }
    }

    private ItemStack getRandomSlimeLoot(ServerLevel level) {
        int roll = level.random.nextInt(6);
        return switch (roll) {
            case 0 -> {
                // Low-level potion
                ItemStack potion = new ItemStack(Items.POTION);
                PotionContents.applyPotionContents(potion, net.minecraft.core.registries.BuiltInRegistries.POTION.wrapAsHolder(
                        level.random.nextBoolean() ? Potions.HEALING : Potions.REGENERATION));
                yield potion;
            }
            case 1 -> new ItemStack(Items.GOLD_NUGGET, 1 + level.random.nextInt(5)); // Coin (nugget)
            case 2 -> {
                // Random nugget
                Item nuggetItem = level.random.nextBoolean() ? Items.IRON_NUGGET : Items.GOLD_NUGGET;
                yield new ItemStack(nuggetItem, 1 + level.random.nextInt(3));
            }
            case 3 -> new ItemStack(Items.EMERALD);
            case 4 -> new ItemStack(Items.STRING, 1 + level.random.nextInt(3));
            default -> new ItemStack(Items.SLIME_BALL); // Fallback
        };
    }
}
