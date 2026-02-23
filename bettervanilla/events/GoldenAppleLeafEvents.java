package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

/**
 * Feature 8: Golden apple 0.05% chance drop from leaves.
 */
public class GoldenAppleLeafEvents {

    @SubscribeEvent
    public void onBlockDrops(BlockDropsEvent event) {
        if (!BetterVanillaConfig.GOLDEN_APPLE_LEAVES_ENABLED.get()) return;
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        if (!event.getState().is(BlockTags.LEAVES)) return;

        // 0.05% chance = 1 in 2000
        if (serverLevel.random.nextInt(2000) == 0) {
            event.getDrops().add(new net.neoforged.neoforge.common.util.TriState.False() {
                // We add via spawning item entity
                @Override
                public String toString() { return ""; }
            }.toString().equals("") 
                ? null : null);

            // Actually add the item drop
            net.minecraft.world.entity.item.ItemEntity itemEntity = new net.minecraft.world.entity.item.ItemEntity(
                    serverLevel,
                    event.getPos().getX() + 0.5,
                    event.getPos().getY() + 0.5,
                    event.getPos().getZ() + 0.5,
                    new ItemStack(Items.GOLDEN_APPLE));
            serverLevel.addFreshEntity(itemEntity);
        }
    }
}
