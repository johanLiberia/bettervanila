package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Optional;

/**
 * Feature 14: Mobs/players with axes apply debuff on hit.
 * Default: Weakness effect for 15 seconds.
 * Configurable: effect, duration, amplifier.
 */
public class AxeDebuffEvents {

    @SubscribeEvent
    public void onAxeHit(LivingDamageEvent.Pre event) {
        if (!BetterVanillaConfig.AXE_DEBUFF_ENABLED.get()) return;

        net.minecraft.world.damagesource.DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof LivingEntity attacker)) return;

        ItemStack weapon = attacker.getMainHandItem();
        if (!(weapon.getItem() instanceof AxeItem)) return;

        if (!(event.getEntity() instanceof LivingEntity target)) return;

        // Get configured effect
        String effectId = BetterVanillaConfig.AXE_DEBUFF_EFFECT.get();
        ResourceLocation rl = ResourceLocation.tryParse(effectId);

        MobEffect effect = MobEffects.WEAKNESS;
        if (rl != null && BuiltInRegistries.MOB_EFFECT.containsKey(rl)) {
            MobEffect configured = BuiltInRegistries.MOB_EFFECT.get(rl);
            if (configured != null) effect = configured;
        }

        int durationTicks = BetterVanillaConfig.AXE_DEBUFF_DURATION_SECONDS.get() * 20;
        int amplifier = BetterVanillaConfig.AXE_DEBUFF_AMPLIFIER.get();

        target.addEffect(new MobEffectInstance(effect.builtInRegistryHolder(), durationTicks, amplifier));
    }
}
