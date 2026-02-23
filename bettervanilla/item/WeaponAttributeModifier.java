package com.yourmod.bettervanilla.item;

import com.yourmod.bettervanilla.BetterVanillaMod;
import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.neoforged.fml.config.ModConfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Modifies weapon base stats at startup.
 * Uses reflection to change final fields in vanilla item classes.
 * Also registers item tooltips via TooltipEvent.
 */
public class WeaponAttributeModifier {

    // Map of item -> [damage bonus, speed]  for tooltip display
    public static final Map<Item, double[]> WEAPON_STATS = new HashMap<>();

    public static void applyWeaponChanges() {
        if (!BetterVanillaConfig.WEAPON_REBALANCE_ENABLED.get()) return;

        // Swords
        setSwordStats(Items.WOODEN_SWORD,  BetterVanillaConfig.WOODEN_SWORD_DAMAGE.get(),  BetterVanillaConfig.WOODEN_SWORD_SPEED.get());
        setSwordStats(Items.STONE_SWORD,   BetterVanillaConfig.STONE_SWORD_DAMAGE.get(),   BetterVanillaConfig.STONE_SWORD_SPEED.get());
        setSwordStats(Items.IRON_SWORD,    BetterVanillaConfig.IRON_SWORD_DAMAGE.get(),    BetterVanillaConfig.IRON_SWORD_SPEED.get());
        setSwordStats(Items.GOLDEN_SWORD,  BetterVanillaConfig.GOLDEN_SWORD_DAMAGE.get(),  BetterVanillaConfig.GOLDEN_SWORD_SPEED.get());
        setSwordStats(Items.DIAMOND_SWORD, BetterVanillaConfig.DIAMOND_SWORD_DAMAGE.get(), BetterVanillaConfig.DIAMOND_SWORD_SPEED.get());
        setSwordStats(Items.NETHERITE_SWORD, BetterVanillaConfig.NETHERITE_SWORD_DAMAGE.get(), BetterVanillaConfig.NETHERITE_SWORD_SPEED.get());

        // Axes
        setAxeStats(Items.WOODEN_AXE,   BetterVanillaConfig.WOODEN_AXE_DAMAGE.get(),   BetterVanillaConfig.WOODEN_AXE_SPEED.get());
        setAxeStats(Items.STONE_AXE,    BetterVanillaConfig.STONE_AXE_DAMAGE.get(),    BetterVanillaConfig.STONE_AXE_SPEED.get());
        setAxeStats(Items.IRON_AXE,     BetterVanillaConfig.IRON_AXE_DAMAGE.get(),     BetterVanillaConfig.IRON_AXE_SPEED.get());
        setAxeStats(Items.GOLDEN_AXE,   BetterVanillaConfig.GOLDEN_AXE_DAMAGE.get(),   BetterVanillaConfig.GOLDEN_AXE_SPEED.get());
        setAxeStats(Items.DIAMOND_AXE,  BetterVanillaConfig.DIAMOND_AXE_DAMAGE.get(),  BetterVanillaConfig.DIAMOND_AXE_SPEED.get());
        setAxeStats(Items.NETHERITE_AXE, BetterVanillaConfig.NETHERITE_AXE_DAMAGE.get(), BetterVanillaConfig.NETHERITE_AXE_SPEED.get());

        // Trident
        setTridentDamage(Items.TRIDENT, BetterVanillaConfig.TRIDENT_DAMAGE.get());

        // Shield
        setShieldDurability(Items.SHIELD, BetterVanillaConfig.SHIELD_DURABILITY.get());

        BetterVanillaMod.LOGGER.info("Weapon rebalance applied.");
    }

    private static void setSwordStats(Item item, double damage, double speed) {
        try {
            Field damageField = SwordItem.class.getDeclaredField("attackDamageBaseline");
            damageField.setAccessible(true);
            damageField.set(item, (float) damage);

            // Store for tooltip
            WEAPON_STATS.put(item, new double[]{damage + 1.0, speed + 4.0}); // +1 from player base, speed = 4 + modifier
        } catch (Exception e) {
            // Try with Minecraft's mapped name
            try {
                // Try obfuscated field name - this may need AT or mixin in production
                Field[] fields = SwordItem.class.getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    Object val = f.get(item);
                    if (val instanceof Float floatVal && floatVal >= 1.0f && floatVal <= 10.0f) {
                        f.set(item, (float) damage);
                        break;
                    }
                }
                WEAPON_STATS.put(item, new double[]{damage + 1.0, speed + 4.0});
            } catch (Exception ex) {
                BetterVanillaMod.LOGGER.error("Failed to set sword stats for " + item + ": " + ex.getMessage());
            }
        }
    }

    private static void setAxeStats(Item item, double damage, double speed) {
        try {
            Field[] fields = AxeItem.class.getDeclaredFields();
            Field damageField = null;
            Field speedField = null;
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getType() == float.class || f.getType() == double.class) {
                    Object val = f.get(item);
                    if (val instanceof Float fv) {
                        if (fv >= 3.0f && fv <= 15.0f && damageField == null) damageField = f;
                        else if (fv <= 0.0f && speedField == null) speedField = f;
                    }
                }
            }
            if (damageField != null) damageField.set(item, (float) damage);
            if (speedField != null) speedField.set(item, (float) speed);
            WEAPON_STATS.put(item, new double[]{damage + 1.0, speed + 4.0});
        } catch (Exception e) {
            BetterVanillaMod.LOGGER.error("Failed to set axe stats: " + e.getMessage());
        }
    }

    private static void setTridentDamage(Item item, double damage) {
        try {
            Field[] fields = TridentItem.class.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getType() == float.class || f.getType() == double.class) {
                    f.set(item, (float) damage);
                    break;
                }
            }
            WEAPON_STATS.put(item, new double[]{damage + 1.0, 4.0 - 1.8}); // trident default speed
        } catch (Exception e) {
            BetterVanillaMod.LOGGER.error("Failed to set trident stats: " + e.getMessage());
        }
    }

    private static void setShieldDurability(Item item, int durability) {
        try {
            Field maxDamageField = Item.class.getDeclaredField("maxDamage");
            maxDamageField.setAccessible(true);
            maxDamageField.set(item, durability);
        } catch (Exception e) {
            BetterVanillaMod.LOGGER.error("Failed to set shield durability: " + e.getMessage());
        }
    }

    /**
     * Get total attack damage for display (includes player base of 1)
     */
    public static double getDisplayDamage(Item item) {
        double[] stats = WEAPON_STATS.get(item);
        return stats != null ? stats[0] : -1;
    }

    public static double getDisplaySpeed(Item item) {
        double[] stats = WEAPON_STATS.get(item);
        return stats != null ? stats[1] : -1;
    }
}
