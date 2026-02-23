package com.yourmod.bettervanilla.mixin;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to override SwordItem damage and speed.
 * This is the reliable way to change weapon stats in NeoForge 1.21.
 */
@Mixin(SwordItem.class)
public class SwordItemMixin {
    // Shadow the field if needed - handled at init time via WeaponAttributeModifier
}
