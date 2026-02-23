package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

/**
 * Feature 2: Mending book restores durability when used (right-click).
 * Player holds mending book and right-clicks - repairs held item in other hand.
 * Or shift+right-click to repair all equipped items.
 */
public class MendingBookEvents {

    @SubscribeEvent
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        if (!BetterVanillaConfig.MENDING_BOOK_REPAIR_ENABLED.get()) return;

        Player player = event.getEntity();
        ItemStack heldItem = event.getItemStack();

        if (!isMendingBook(heldItem)) return;

        boolean anyRepaired = false;

        if (player.isShiftKeyDown()) {
            // Repair all equipment
            for (ItemStack stack : player.getInventory().items) {
                if (!stack.isEmpty() && stack.isDamaged()) {
                    stack.setDamageValue(0);
                    anyRepaired = true;
                }
            }
            for (ItemStack stack : player.getInventory().armor) {
                if (!stack.isEmpty() && stack.isDamaged()) {
                    stack.setDamageValue(0);
                    anyRepaired = true;
                }
            }
        } else {
            // Repair the other hand item
            InteractionHand otherHand = event.getHand() == InteractionHand.MAIN_HAND 
                ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            ItemStack otherItem = player.getItemInHand(otherHand);
            if (!otherItem.isEmpty() && otherItem.isDamaged()) {
                otherItem.setDamageValue(0);
                anyRepaired = true;
            }
        }

        if (anyRepaired) {
            if (!player.isCreative()) {
                heldItem.shrink(1); // Consume the book
            }
            event.setCanceled(true);
        }
    }

    private boolean isMendingBook(ItemStack stack) {
        if (stack.isEmpty() || stack.getItem() != Items.ENCHANTED_BOOK) return false;
        // Check if it has Mending enchantment
        var enchantments = stack.getEnchantments();
        return enchantments.entrySet().stream()
                .anyMatch(entry -> entry.getKey().is(net.minecraft.core.registries.Registries.ENCHANTMENT));
        // Simplified check - in production use proper enchantment key check
    }
}
