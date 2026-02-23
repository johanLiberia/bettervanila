package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.monster.raider.AbstractRaider;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * Feature 7: Raiders can break doors, glass, gates, fences.
 */
public class RaiderDoorEvents {

    @SubscribeEvent
    public void onRaiderTick(EntityTickEvent.Post event) {
        if (!BetterVanillaConfig.RAIDER_BREAK_BLOCKS_ENABLED.get()) return;
        if (!(event.getEntity() instanceof AbstractRaider raider)) return;
        if (!(raider.level() instanceof ServerLevel serverLevel)) return;
        if (!raider.hasActiveRaid()) return;
        if (raider.tickCount % 20 != 0) return; // Every second

        // Check blocks directly in front of raider
        BlockPos forward = raider.blockPosition().relative(raider.getDirection());
        BlockPos forwardUp = forward.above();

        tryBreakBlock(serverLevel, forward, raider);
        tryBreakBlock(serverLevel, forwardUp, raider);
    }

    private void tryBreakBlock(ServerLevel level, BlockPos pos, AbstractRaider raider) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        boolean shouldBreak = false;

        if (block instanceof DoorBlock) shouldBreak = true;
        else if (block instanceof GlassBlock) shouldBreak = true;
        else if (block instanceof StainedGlassBlock) shouldBreak = true;
        else if (block instanceof GlassPaneBlock) shouldBreak = true;
        else if (block instanceof StainedGlassPaneBlock) shouldBreak = true;
        else if (block instanceof FenceGateBlock) shouldBreak = true;
        else if (state.is(BlockTags.FENCES)) shouldBreak = true;
        else if (block instanceof TrapDoorBlock) shouldBreak = true;

        if (shouldBreak) {
            level.destroyBlock(pos, true, raider);
        }
    }
}
