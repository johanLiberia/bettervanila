package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import com.yourmod.bettervanilla.item.WeaponAttributeModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

/**
 * Adds weapon stat display to item tooltips.
 * Also handles Better Combat compatibility note.
 */
public class WeaponDamageEvents {

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (!BetterVanillaConfig.WEAPON_REBALANCE_ENABLED.get()) return;

        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        double damage = WeaponAttributeModifier.getDisplayDamage(item);
        double speed = WeaponAttributeModifier.getDisplaySpeed(item);

        if (damage < 0) return; // Not a modified weapon

        // Find and replace existing damage tooltip or add our own
        var tooltips = event.getToolTip();

        // Add custom stats after the item name
        tooltips.add(1, Component.literal("").withStyle(ChatFormatting.GRAY));
        tooltips.add(2, Component.literal("§7[Better Vanilla Stats]").withStyle(ChatFormatting.DARK_GRAY));
        tooltips.add(3, Component.literal(String.format("§c⚔ Урон: %.1f", damage)));
        tooltips.add(4, Component.literal(String.format("§b⚡ Скорость атаки: %.2f", speed)));
        if (item instanceof SwordItem || item instanceof AxeItem) {
            tooltips.add(5, Component.literal("§8(Better Combat совместимо)").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
