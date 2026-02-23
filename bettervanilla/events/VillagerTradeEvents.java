package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.BetterVanillaMod;
import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Feature 1: Villager trades refresh daily with increasing chance.
 * - After reaching min profession level, each game day there's a chance to refresh trades.
 * - The chance increases with each completed trade (multiplier system).
 */
public class VillagerTradeEvents {

    // Track trade counts per villager UUID
    private static final Map<UUID, Integer> tradeCountMap = new HashMap<>();
    // Track last day trades were refreshed
    private static final Map<UUID, Long> lastRefreshDay = new HashMap<>();

    @SubscribeEvent
    public void onLevelTick(LevelTickEvent.Post event) {
        if (!BetterVanillaConfig.VILLAGER_TRADE_REFRESH_ENABLED.get()) return;
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        long currentDay = serverLevel.getDayTime() / 24000L;

        serverLevel.getEntities().getAll().forEach(entity -> {
            if (!(entity instanceof Villager villager)) return;

            VillagerData data = villager.getVillagerData();
            if (data.getLevel() < BetterVanillaConfig.VILLAGER_MIN_LEVEL_FOR_REFRESH.get()) return;

            UUID id = villager.getUUID();
            long lastDay = lastRefreshDay.getOrDefault(id, -1L);
            if (lastDay >= currentDay) return; // Already processed today

            lastRefreshDay.put(id, currentDay);

            int trades = tradeCountMap.getOrDefault(id, 0);
            double minChance = BetterVanillaConfig.VILLAGER_TRADE_MIN_CHANCE.get();
            double maxChance = BetterVanillaConfig.VILLAGER_TRADE_MAX_CHANCE.get();
            double multiplier = BetterVanillaConfig.VILLAGER_TRADE_MULTIPLIER_PER_TRADE.get();

            double chance = Math.min(minChance + (trades * multiplier), maxChance);
            double roll = serverLevel.random.nextDouble() * 100.0;

            if (roll < chance) {
                villager.resetTrades();
                BetterVanillaMod.LOGGER.debug("Refreshed trades for villager " + id + " (chance: " + String.format("%.2f", chance) + "%)");
            }
        });
    }

    /**
     * Call this when a villager completes a trade to increment counter.
     */
    public static void onTradeDone(UUID villagerUUID) {
        tradeCountMap.merge(villagerUUID, 1, Integer::sum);
    }
}
