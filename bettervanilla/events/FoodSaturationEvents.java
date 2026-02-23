package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FoodLevelChangeEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.*;

/**
 * Feature 3: Food saturation penalty.
 * - Eating a stack of the same food type reduces food effectiveness for 4 game days.
 * - Eating 16 units of different food cures the penalty faster.
 * 
 * Tracks via persistent player data (CompoundTag on player).
 */
public class FoodSaturationEvents {

    private static final String TAG_KEY = "bettervanilla_food";
    private static final String FOOD_COUNT_TAG = "sameFood_count";
    private static final String FOOD_TYPE_TAG = "sameFood_type";
    private static final String PENALTY_END_DAY_TAG = "penalty_end_day";
    private static final String RECOVERY_COUNT_TAG = "recovery_count";
    private static final String HAS_PENALTY_TAG = "has_penalty";

    @SubscribeEvent
    public void onFoodEaten(FoodLevelChangeEvent event) {
        if (!BetterVanillaConfig.FOOD_SATURATION_PENALTY_ENABLED.get()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ItemStack foodStack = player.getMainHandItem().isEmpty() 
            ? player.getOffhandItem() : player.getMainHandItem();
        if (foodStack.isEmpty() || !foodStack.getItem().isEdible()) return;

        CompoundTag data = getPlayerData(player);
        String currentFoodId = getItemId(foodStack.getItem());
        String lastFoodId = data.getString(FOOD_TYPE_TAG);

        if (currentFoodId.equals(lastFoodId)) {
            int count = data.getInt(FOOD_COUNT_TAG) + 1;
            data.putInt(FOOD_COUNT_TAG, count);

            // Check if player has penalty
            if (data.getBoolean(HAS_PENALTY_TAG)) {
                // Already penalized; track recovery
                int recoveryCount = data.getInt(RECOVERY_COUNT_TAG) + 1;
                data.putInt(RECOVERY_COUNT_TAG, recoveryCount);

                if (recoveryCount >= BetterVanillaConfig.FOOD_RECOVERY_UNITS.get()) {
                    removePenalty(data, player);
                }
            }

            // Trigger penalty when a full stack (64) or config threshold eaten
            if (count >= 64) {
                applyPenalty(data, player);
                data.putInt(FOOD_COUNT_TAG, 0);
            }
        } else {
            // Different food - reset same-food counter, increment recovery counter
            data.putString(FOOD_TYPE_TAG, currentFoodId);
            data.putInt(FOOD_COUNT_TAG, 1);

            if (data.getBoolean(HAS_PENALTY_TAG)) {
                int recoveryCount = data.getInt(RECOVERY_COUNT_TAG) + 1;
                data.putInt(RECOVERY_COUNT_TAG, recoveryCount);

                if (recoveryCount >= BetterVanillaConfig.FOOD_RECOVERY_UNITS.get()) {
                    removePenalty(data, player);
                }
            }
        }

        savePlayerData(player, data);
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        if (!BetterVanillaConfig.FOOD_SATURATION_PENALTY_ENABLED.get()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % 20 != 0) return; // Check once per second

        CompoundTag data = getPlayerData(player);
        if (!data.getBoolean(HAS_PENALTY_TAG)) return;

        long penaltyEndDay = data.getLong(PENALTY_END_DAY_TAG);
        long currentDay = player.level().getDayTime() / 24000L;

        if (currentDay >= penaltyEndDay) {
            removePenalty(data, player);
            savePlayerData(player, data);
        }
    }

    private void applyPenalty(CompoundTag data, ServerPlayer player) {
        long currentDay = player.level().getDayTime() / 24000L;
        long endDay = currentDay + BetterVanillaConfig.FOOD_PENALTY_DURATION_DAYS.get();
        data.putBoolean(HAS_PENALTY_TAG, true);
        data.putLong(PENALTY_END_DAY_TAG, endDay);
        data.putInt(RECOVERY_COUNT_TAG, 0);

        // Apply weakness/hunger effect to indicate penalty
        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.HUNGER, 
                BetterVanillaConfig.FOOD_PENALTY_DURATION_DAYS.get() * 24000, 0));

        player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                "§cВы переели! Эффективность еды снижена на " + 
                BetterVanillaConfig.FOOD_PENALTY_DURATION_DAYS.get() + " игровых дней."));
    }

    private void removePenalty(CompoundTag data, ServerPlayer player) {
        data.putBoolean(HAS_PENALTY_TAG, false);
        data.putLong(PENALTY_END_DAY_TAG, 0);
        data.putInt(RECOVERY_COUNT_TAG, 0);
        player.removeEffect(net.minecraft.world.effect.MobEffects.HUNGER);
        player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                "§aЭффект переедания прошёл!"));
    }

    public static boolean hasFoodPenalty(ServerPlayer player) {
        return getPlayerData(player).getBoolean(HAS_PENALTY_TAG);
    }

    private static CompoundTag getPlayerData(ServerPlayer player) {
        CompoundTag persistentData = player.getPersistentData();
        if (!persistentData.contains(TAG_KEY)) {
            persistentData.put(TAG_KEY, new CompoundTag());
        }
        return persistentData.getCompound(TAG_KEY);
    }

    private static void savePlayerData(ServerPlayer player, CompoundTag data) {
        player.getPersistentData().put(TAG_KEY, data);
    }

    private static String getItemId(Item item) {
        return Objects.requireNonNull(item.builtInRegistryHolder().key()).location().toString();
    }
}
