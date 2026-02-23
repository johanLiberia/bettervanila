package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * Feature 11: Piglin Brute (with axe) deals more damage if player has no gold armor,
 *             and less damage if player has gold armor.
 */
public class PiglinDamageEvents {

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!BetterVanillaConfig.PIGLIN_AXE_DAMAGE_ENABLED.get()) return;
        if (!(event.getEntity() instanceof Player player)) return;
        if (!(event.getSource().getEntity() instanceof PiglinBrute)) return;

        boolean hasGoldArmor = hasGoldenArmor(player);

        if (hasGoldArmor) {
            // Reduce damage by 50%
            event.setNewDamage(event.getNewDamage() * 0.5f);
        } else {
            // Increase damage by 50%
            event.setNewDamage(event.getNewDamage() * 1.5f);
        }
    }

    private boolean hasGoldenArmor(Player player) {
        for (ItemStack armorPiece : player.getArmorSlots()) {
            if (!armorPiece.isEmpty() && armorPiece.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial().is(ArmorMaterials.GOLD)) {
                    return true;
                }
            }
        }
        return false;
    }
}
