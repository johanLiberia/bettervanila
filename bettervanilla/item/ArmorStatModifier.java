package com.yourmod.bettervanilla.item;

import com.yourmod.bettervanilla.BetterVanillaMod;
import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Modifies armor stats via reflection on ArmorItem.
 * For NeoForge 1.21.1 the armor type / material holds stats.
 */
public class ArmorStatModifier {

    public static void applyArmorChanges() {
        if (!BetterVanillaConfig.ARMOR_REBALANCE_ENABLED.get()) return;

        // Leather
        modifyArmorDefense(Items.LEATHER_CHESTPLATE, BetterVanillaConfig.LEATHER_CHESTPLATE_ARMOR.get(), 0);
        modifyArmorDefense(Items.LEATHER_LEGGINGS,   BetterVanillaConfig.LEATHER_LEGGINGS_ARMOR.get(), 0);

        // Chainmail
        modifyArmorDefense(Items.CHAINMAIL_CHESTPLATE, BetterVanillaConfig.CHAINMAIL_ARMOR.get(), 0);
        modifyArmorDefense(Items.CHAINMAIL_LEGGINGS,   BetterVanillaConfig.CHAINMAIL_LEGGINGS_ARMOR.get(), 0);

        // Iron
        modifyArmorDefense(Items.IRON_CHESTPLATE, BetterVanillaConfig.IRON_CHESTPLATE_ARMOR.get(), 0);
        modifyArmorDefense(Items.IRON_LEGGINGS,   BetterVanillaConfig.IRON_LEGGINGS_ARMOR.get(), 0);

        // Gold
        modifyArmorDefense(Items.GOLDEN_CHESTPLATE, BetterVanillaConfig.GOLDEN_CHESTPLATE_ARMOR.get(), 0);

        // Diamond
        modifyArmorDefense(Items.DIAMOND_HELMET,     -1, BetterVanillaConfig.DIAMOND_HELMET_TOUGHNESS.get());
        modifyArmorDefense(Items.DIAMOND_CHESTPLATE, BetterVanillaConfig.DIAMOND_CHESTPLATE_ARMOR.get(), BetterVanillaConfig.DIAMOND_CHESTPLATE_TOUGHNESS.get());
        modifyArmorDefense(Items.DIAMOND_LEGGINGS,   BetterVanillaConfig.DIAMOND_LEGGINGS_ARMOR.get(), BetterVanillaConfig.DIAMOND_LEGGINGS_TOUGHNESS.get());
        modifyArmorDefense(Items.DIAMOND_BOOTS,      -1, BetterVanillaConfig.DIAMOND_BOOTS_TOUGHNESS.get());

        // Netherite
        modifyArmorDefense(Items.NETHERITE_HELMET,     -1, BetterVanillaConfig.NETHERITE_HELMET_TOUGHNESS.get());
        modifyArmorDefense(Items.NETHERITE_CHESTPLATE, BetterVanillaConfig.NETHERITE_CHESTPLATE_ARMOR.get(), BetterVanillaConfig.NETHERITE_CHESTPLATE_TOUGHNESS.get());
        modifyArmorDefense(Items.NETHERITE_LEGGINGS,   BetterVanillaConfig.NETHERITE_LEGGINGS_ARMOR.get(), BetterVanillaConfig.NETHERITE_LEGGINGS_TOUGHNESS.get());
        modifyArmorDefense(Items.NETHERITE_BOOTS,      -1, BetterVanillaConfig.NETHERITE_BOOTS_TOUGHNESS.get());

        // Turtle
        modifyArmorDefense(Items.TURTLE_HELMET, BetterVanillaConfig.TURTLE_HELMET_ARMOR.get(), BetterVanillaConfig.TURTLE_HELMET_TOUGHNESS.get());

        BetterVanillaMod.LOGGER.info("Armor rebalance applied.");
    }

    private static void modifyArmorDefense(Item item, double defense, double toughness) {
        if (!(item instanceof ArmorItem armor)) return;
        try {
            // Rebuild the attribute modifiers component
            EquipmentSlot slot = armor.getEquipmentSlot();
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

            if (defense >= 0) {
                builder.add(Attributes.ARMOR,
                        new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("bettervanilla", "armor_defense_" + item.getDescriptionId()),
                                defense,
                                net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE),
                        slot.getFilterFlag());
            }
            if (toughness > 0) {
                builder.add(Attributes.ARMOR_TOUGHNESS,
                        new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("bettervanilla", "armor_toughness_" + item.getDescriptionId()),
                                toughness,
                                net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE),
                        slot.getFilterFlag());
            }

            // For netherite, also add knockback resistance
            addKnockbackResistance(builder, item, slot);

        } catch (Exception e) {
            BetterVanillaMod.LOGGER.error("Failed to modify armor stats for " + item + ": " + e.getMessage());
        }
    }

    private static void addKnockbackResistance(ItemAttributeModifiers.Builder builder, Item item, EquipmentSlot slot) {
        double kbr = 0;
        if (item == Items.NETHERITE_HELMET)     kbr = BetterVanillaConfig.NETHERITE_HELMET_KNOCKBACK_RES.get();
        if (item == Items.NETHERITE_CHESTPLATE) kbr = BetterVanillaConfig.NETHERITE_CHESTPLATE_KNOCKBACK_RES.get();
        if (item == Items.NETHERITE_LEGGINGS)   kbr = BetterVanillaConfig.NETHERITE_LEGGINGS_KNOCKBACK_RES.get();
        if (item == Items.NETHERITE_BOOTS)      kbr = BetterVanillaConfig.NETHERITE_BOOTS_KNOCKBACK_RES.get();

        if (kbr > 0) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE,
                    new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("bettervanilla", "knockback_res_" + item.getDescriptionId()),
                            kbr,
                            net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE),
                    slot.getFilterFlag());
        }
    }
}
