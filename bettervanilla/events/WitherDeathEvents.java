package com.yourmod.bettervanilla.events;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * Feature 10: On Wither death, spawn 3 black (Wither) skeletons with equipment.
 */
public class WitherDeathEvents {

    @SubscribeEvent
    public void onWitherDeath(LivingDeathEvent event) {
        if (!BetterVanillaConfig.WITHER_DEATH_SPAWN_ENABLED.get()) return;
        if (!(event.getEntity() instanceof WitherBoss wither)) return;
        if (!(wither.level() instanceof ServerLevel serverLevel)) return;

        for (int i = 0; i < 3; i++) {
            WitherSkeleton skeleton = EntityType.WITHER_SKELETON.create(serverLevel);
            if (skeleton == null) continue;

            double offsetX = (serverLevel.random.nextDouble() - 0.5) * 4;
            double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 4;
            skeleton.moveTo(wither.getX() + offsetX, wither.getY(), wither.getZ() + offsetZ, serverLevel.random.nextFloat() * 360, 0);

            // Equip with iron armor and stone sword
            skeleton.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
            skeleton.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
            skeleton.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));

            // Mark as persistent
            skeleton.setPersistenceRequired();

            serverLevel.addFreshEntity(skeleton);
        }
    }
}
