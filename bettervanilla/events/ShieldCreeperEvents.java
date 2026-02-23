package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Feature 15 (shield): Creeper explosion damages shield.
 * Two creeper explosions while blocking break the shield.
 */
public class ShieldCreeperEvents {

    // Track how many hits the shield has taken from creepers per player
    private static final Map<UUID, Integer> shieldHitsMap = new HashMap<>();

    @SubscribeEvent
    public void onPlayerDamaged(LivingDamageEvent.Pre event) {
        if (!BetterVanillaConfig.SHIELD_CREEPER_DAMAGE_ENABLED.get()) return;
        if (!(event.getEntity() instanceof Player player)) return;

        // Check if player is blocking with shield
        if (!player.isBlocking()) return;

        // Check if source is explosion from creeper
        net.minecraft.world.damagesource.DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof Creeper)) return;

        ItemStack shield = player.getUseItem();
        if (shield.getItem() != Items.SHIELD) return;

        UUID uid = player.getUUID();
        int hits = shieldHitsMap.getOrDefault(uid, 0) + 1;
        shieldHitsMap.put(uid, hits);

        if (hits >= 2) {
            // Break the shield regardless of durability
            shield.setDamageValue(shield.getMaxDamage() - 1);
            shield.hurtAndBreak(1, player, net.minecraft.world.entity.EquipmentSlot.OFFHAND);
            shieldHitsMap.put(uid, 0);
        } else {
            // Deal heavy durability damage to shield
            shield.hurtAndBreak(shield.getMaxDamage() / 2, player, net.minecraft.world.entity.EquipmentSlot.OFFHAND);
        }
    }
}
