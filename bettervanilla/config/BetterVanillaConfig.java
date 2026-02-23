package com.yourmod.bettervanilla.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BetterVanillaConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    // Feature toggles
    public static final ModConfigSpec.BooleanValue VILLAGER_TRADE_REFRESH_ENABLED;
    public static final ModConfigSpec.BooleanValue MENDING_BOOK_REPAIR_ENABLED;
    public static final ModConfigSpec.BooleanValue FOOD_SATURATION_PENALTY_ENABLED;
    public static final ModConfigSpec.BooleanValue RAID_KILL_PASSIVE_MOBS_ENABLED;
    public static final ModConfigSpec.BooleanValue SLIME_RAIN_SPAWN_ENABLED;
    public static final ModConfigSpec.BooleanValue SLIME_MERGE_ENABLED;
    public static final ModConfigSpec.BooleanValue SLIME_LOOT_ENABLED;
    public static final ModConfigSpec.BooleanValue MAGMA_CUBE_OVERWORLD_SPAWN_ENABLED;
    public static final ModConfigSpec.BooleanValue VILLAGER_FIGHT_BACK_ENABLED;
    public static final ModConfigSpec.BooleanValue RAIDER_BREAK_BLOCKS_ENABLED;
    public static final ModConfigSpec.BooleanValue GOLDEN_APPLE_LEAVES_ENABLED;
    public static final ModConfigSpec.BooleanValue PHANTOM_DEATH_SPAWN_ENABLED;
    public static final ModConfigSpec.BooleanValue WITHER_DEATH_SPAWN_ENABLED;
    public static final ModConfigSpec.BooleanValue PIGLIN_AXE_DAMAGE_ENABLED;
    public static final ModConfigSpec.BooleanValue KILLER_RABBIT_REPLACE_ENABLED;
    public static final ModConfigSpec.BooleanValue DARK_MINE_ZOMBIE_ENABLED;
    public static final ModConfigSpec.BooleanValue AXE_DEBUFF_ENABLED;
    public static final ModConfigSpec.BooleanValue WEAPON_REBALANCE_ENABLED;
    public static final ModConfigSpec.BooleanValue SHIELD_CREEPER_DAMAGE_ENABLED;
    public static final ModConfigSpec.BooleanValue ARMOR_REBALANCE_ENABLED;

    // Villager trade config
    public static final ModConfigSpec.DoubleValue VILLAGER_TRADE_MIN_CHANCE;
    public static final ModConfigSpec.DoubleValue VILLAGER_TRADE_MAX_CHANCE;
    public static final ModConfigSpec.DoubleValue VILLAGER_TRADE_MULTIPLIER_PER_TRADE;
    public static final ModConfigSpec.IntValue VILLAGER_MIN_LEVEL_FOR_REFRESH;

    // Food saturation config
    public static final ModConfigSpec.IntValue FOOD_PENALTY_DURATION_DAYS;
    public static final ModConfigSpec.IntValue FOOD_RECOVERY_UNITS;

    // Slime loot config
    public static final ModConfigSpec.DoubleValue SLIME_LOOT_CHANCE;

    // Phantom death config
    public static final ModConfigSpec.StringValue PHANTOM_DEATH_MOB;

    // Killer rabbit config
    public static final ModConfigSpec.DoubleValue KILLER_RABBIT_SPAWN_CHANCE;

    // Axe debuff config
    public static final ModConfigSpec.IntValue AXE_DEBUFF_DURATION_SECONDS;
    public static final ModConfigSpec.IntValue AXE_DEBUFF_AMPLIFIER;
    public static final ModConfigSpec.StringValue AXE_DEBUFF_EFFECT;

    // Weapon stats
    // Swords
    public static final ModConfigSpec.DoubleValue WOODEN_SWORD_DAMAGE;
    public static final ModConfigSpec.DoubleValue WOODEN_SWORD_SPEED;
    public static final ModConfigSpec.DoubleValue STONE_SWORD_DAMAGE;
    public static final ModConfigSpec.DoubleValue STONE_SWORD_SPEED;
    public static final ModConfigSpec.DoubleValue IRON_SWORD_DAMAGE;
    public static final ModConfigSpec.DoubleValue IRON_SWORD_SPEED;
    public static final ModConfigSpec.DoubleValue GOLDEN_SWORD_DAMAGE;
    public static final ModConfigSpec.DoubleValue GOLDEN_SWORD_SPEED;
    public static final ModConfigSpec.DoubleValue DIAMOND_SWORD_DAMAGE;
    public static final ModConfigSpec.DoubleValue DIAMOND_SWORD_SPEED;
    public static final ModConfigSpec.DoubleValue NETHERITE_SWORD_DAMAGE;
    public static final ModConfigSpec.DoubleValue NETHERITE_SWORD_SPEED;

    // Axes
    public static final ModConfigSpec.DoubleValue WOODEN_AXE_DAMAGE;
    public static final ModConfigSpec.DoubleValue WOODEN_AXE_SPEED;
    public static final ModConfigSpec.DoubleValue STONE_AXE_DAMAGE;
    public static final ModConfigSpec.DoubleValue STONE_AXE_SPEED;
    public static final ModConfigSpec.DoubleValue IRON_AXE_DAMAGE;
    public static final ModConfigSpec.DoubleValue IRON_AXE_SPEED;
    public static final ModConfigSpec.DoubleValue GOLDEN_AXE_DAMAGE;
    public static final ModConfigSpec.DoubleValue GOLDEN_AXE_SPEED;
    public static final ModConfigSpec.DoubleValue DIAMOND_AXE_DAMAGE;
    public static final ModConfigSpec.DoubleValue DIAMOND_AXE_SPEED;
    public static final ModConfigSpec.DoubleValue NETHERITE_AXE_DAMAGE;
    public static final ModConfigSpec.DoubleValue NETHERITE_AXE_SPEED;

    // Mace
    public static final ModConfigSpec.DoubleValue MACE_DAMAGE;
    public static final ModConfigSpec.DoubleValue MACE_REACH;

    // Trident
    public static final ModConfigSpec.DoubleValue TRIDENT_DAMAGE;

    // Shield
    public static final ModConfigSpec.IntValue SHIELD_DURABILITY;

    // Armor stats
    public static final ModConfigSpec.DoubleValue LEATHER_CHESTPLATE_ARMOR;
    public static final ModConfigSpec.DoubleValue LEATHER_LEGGINGS_ARMOR;
    public static final ModConfigSpec.DoubleValue CHAINMAIL_ARMOR;
    public static final ModConfigSpec.DoubleValue CHAINMAIL_LEGGINGS_ARMOR;
    public static final ModConfigSpec.DoubleValue IRON_CHESTPLATE_ARMOR;
    public static final ModConfigSpec.DoubleValue IRON_LEGGINGS_ARMOR;
    public static final ModConfigSpec.DoubleValue GOLDEN_CHESTPLATE_ARMOR;
    public static final ModConfigSpec.DoubleValue DIAMOND_HELMET_TOUGHNESS;
    public static final ModConfigSpec.DoubleValue DIAMOND_CHESTPLATE_ARMOR;
    public static final ModConfigSpec.DoubleValue DIAMOND_CHESTPLATE_TOUGHNESS;
    public static final ModConfigSpec.DoubleValue DIAMOND_LEGGINGS_ARMOR;
    public static final ModConfigSpec.DoubleValue DIAMOND_LEGGINGS_TOUGHNESS;
    public static final ModConfigSpec.DoubleValue DIAMOND_BOOTS_TOUGHNESS;
    public static final ModConfigSpec.DoubleValue NETHERITE_HELMET_TOUGHNESS;
    public static final ModConfigSpec.DoubleValue NETHERITE_HELMET_KNOCKBACK_RES;
    public static final ModConfigSpec.DoubleValue NETHERITE_CHESTPLATE_ARMOR;
    public static final ModConfigSpec.DoubleValue NETHERITE_CHESTPLATE_TOUGHNESS;
    public static final ModConfigSpec.DoubleValue NETHERITE_CHESTPLATE_KNOCKBACK_RES;
    public static final ModConfigSpec.DoubleValue NETHERITE_LEGGINGS_ARMOR;
    public static final ModConfigSpec.DoubleValue NETHERITE_LEGGINGS_TOUGHNESS;
    public static final ModConfigSpec.DoubleValue NETHERITE_LEGGINGS_KNOCKBACK_RES;
    public static final ModConfigSpec.DoubleValue NETHERITE_BOOTS_TOUGHNESS;
    public static final ModConfigSpec.DoubleValue NETHERITE_BOOTS_KNOCKBACK_RES;
    public static final ModConfigSpec.DoubleValue TURTLE_HELMET_ARMOR;
    public static final ModConfigSpec.DoubleValue TURTLE_HELMET_TOUGHNESS;

    static {
        BUILDER.push("features");
        VILLAGER_TRADE_REFRESH_ENABLED = BUILDER.comment("Enable villager trade refresh mechanic").define("villagerTradeRefresh", true);
        MENDING_BOOK_REPAIR_ENABLED = BUILDER.comment("Enable mending book repair mechanic").define("mendingBookRepair", true);
        FOOD_SATURATION_PENALTY_ENABLED = BUILDER.comment("Enable food saturation penalty").define("foodSaturationPenalty", true);
        RAID_KILL_PASSIVE_MOBS_ENABLED = BUILDER.comment("Enable raiders killing passive mobs").define("raidKillPassiveMobs", true);
        SLIME_RAIN_SPAWN_ENABLED = BUILDER.comment("Enable slime rain spawning in swamps").define("slimeRainSpawn", true);
        SLIME_MERGE_ENABLED = BUILDER.comment("Enable small slimes merging into big slime").define("slimeMerge", true);
        SLIME_LOOT_ENABLED = BUILDER.comment("Enable slime/magma cube special loot").define("slimeLoot", true);
        MAGMA_CUBE_OVERWORLD_SPAWN_ENABLED = BUILDER.comment("Enable magma cube spawning near lava in overworld").define("magmaCubeOverworldSpawn", true);
        VILLAGER_FIGHT_BACK_ENABLED = BUILDER.comment("Enable villagers fighting back when attacked").define("villagerFightBack", true);
        RAIDER_BREAK_BLOCKS_ENABLED = BUILDER.comment("Enable raiders breaking doors/fences/glass").define("raiderBreakBlocks", true);
        GOLDEN_APPLE_LEAVES_ENABLED = BUILDER.comment("Enable golden apple drop from leaves").define("goldenAppleLeaves", true);
        PHANTOM_DEATH_SPAWN_ENABLED = BUILDER.comment("Enable mob spawn on phantom death").define("phantomDeathSpawn", true);
        WITHER_DEATH_SPAWN_ENABLED = BUILDER.comment("Enable black skeletons spawn on wither death").define("witherDeathSpawn", true);
        PIGLIN_AXE_DAMAGE_ENABLED = BUILDER.comment("Enable piglin brute bonus damage").define("piglinAxeDamage", true);
        KILLER_RABBIT_REPLACE_ENABLED = BUILDER.comment("Enable killer rabbit replacing normal rabbits").define("killerRabbitReplace", true);
        DARK_MINE_ZOMBIE_ENABLED = BUILDER.comment("Enable torch zombie in dark mines").define("darkMineZombie", true);
        AXE_DEBUFF_ENABLED = BUILDER.comment("Enable axe debuff on hit").define("axeDebuff", true);
        WEAPON_REBALANCE_ENABLED = BUILDER.comment("Enable weapon stat rebalance").define("weaponRebalance", true);
        SHIELD_CREEPER_DAMAGE_ENABLED = BUILDER.comment("Enable creeper explosion damaging shield").define("shieldCreeperDamage", true);
        ARMOR_REBALANCE_ENABLED = BUILDER.comment("Enable armor stat rebalance").define("armorRebalance", true);
        BUILDER.pop();

        BUILDER.push("villagerTrade");
        VILLAGER_TRADE_MIN_CHANCE = BUILDER.comment("Minimum chance (%) per day for trade refresh").defineInRange("minChance", 0.5, 0.1, 50.0);
        VILLAGER_TRADE_MAX_CHANCE = BUILDER.comment("Maximum chance (%) per day for trade refresh").defineInRange("maxChance", 99.0, 10.0, 99.0);
        VILLAGER_TRADE_MULTIPLIER_PER_TRADE = BUILDER.comment("Multiplier increase per trade (%)").defineInRange("multiplierPerTrade", 0.5, 0.01, 10.0);
        VILLAGER_MIN_LEVEL_FOR_REFRESH = BUILDER.comment("Minimum villager level for trade refresh").defineInRange("minLevel", 2, 1, 5);
        BUILDER.pop();

        BUILDER.push("food");
        FOOD_PENALTY_DURATION_DAYS = BUILDER.comment("Days food penalty lasts").defineInRange("penaltyDays", 4, 1, 30);
        FOOD_RECOVERY_UNITS = BUILDER.comment("Units of different food needed to recover").defineInRange("recoveryUnits", 16, 1, 64);
        BUILDER.pop();

        BUILDER.push("slime");
        SLIME_LOOT_CHANCE = BUILDER.comment("Chance (%) for slime/magma cube to drop special loot").defineInRange("lootChance", 5.0, 0.1, 100.0);
        BUILDER.pop();

        BUILDER.push("phantom");
        PHANTOM_DEATH_MOB = BUILDER.comment("Mob to spawn on phantom death (vex or other modded mob id)").define("deathMob", "minecraft:vex");
        BUILDER.pop();

        BUILDER.push("killerRabbit");
        KILLER_RABBIT_SPAWN_CHANCE = BUILDER.comment("Chance (%) for rabbit to be killer rabbit").defineInRange("spawnChance", 5.0, 0.1, 100.0);
        BUILDER.pop();

        BUILDER.push("axeDebuff");
        AXE_DEBUFF_DURATION_SECONDS = BUILDER.comment("Duration of axe debuff in seconds").defineInRange("durationSeconds", 15, 1, 120);
        AXE_DEBUFF_AMPLIFIER = BUILDER.comment("Amplifier (level) of axe debuff effect (0 = level 1)").defineInRange("amplifier", 0, 0, 10);
        AXE_DEBUFF_EFFECT = BUILDER.comment("Effect ID for axe debuff (e.g. minecraft:weakness)").define("effectId", "minecraft:weakness");
        BUILDER.pop();

        BUILDER.push("weapons");
        WOODEN_SWORD_DAMAGE = BUILDER.comment("Wooden sword damage bonus (total = this + 1 from base)").defineInRange("woodenSwordDamage", 3.0, 0.0, 20.0);
        WOODEN_SWORD_SPEED = BUILDER.comment("Wooden sword attack speed").defineInRange("woodenSwordSpeed", -2.4, -4.0, 4.0);
        STONE_SWORD_DAMAGE = BUILDER.defineInRange("stoneSwordDamage", 4.0, 0.0, 20.0);
        STONE_SWORD_SPEED = BUILDER.defineInRange("stoneSwordSpeed", -2.4, -4.0, 4.0);
        IRON_SWORD_DAMAGE = BUILDER.defineInRange("ironSwordDamage", 5.0, 0.0, 20.0);
        IRON_SWORD_SPEED = BUILDER.defineInRange("ironSwordSpeed", -2.4, -4.0, 4.0);
        GOLDEN_SWORD_DAMAGE = BUILDER.defineInRange("goldenSwordDamage", 3.5, 0.0, 20.0);
        GOLDEN_SWORD_SPEED = BUILDER.defineInRange("goldenSwordSpeed", -1.6, -4.0, 4.0);
        DIAMOND_SWORD_DAMAGE = BUILDER.defineInRange("diamondSwordDamage", 6.0, 0.0, 20.0);
        DIAMOND_SWORD_SPEED = BUILDER.defineInRange("diamondSwordSpeed", -2.4, -4.0, 4.0);
        NETHERITE_SWORD_DAMAGE = BUILDER.defineInRange("netheriteSwordDamage", 7.0, 0.0, 20.0);
        NETHERITE_SWORD_SPEED = BUILDER.defineInRange("netheriteSwordSpeed", -2.4, -4.0, 4.0);

        WOODEN_AXE_DAMAGE = BUILDER.defineInRange("woodenAxeDamage", 5.5, 0.0, 20.0);
        WOODEN_AXE_SPEED = BUILDER.defineInRange("woodenAxeSpeed", -3.5, -4.0, 4.0);
        STONE_AXE_DAMAGE = BUILDER.defineInRange("stoneAxeDamage", 6.5, 0.0, 20.0);
        STONE_AXE_SPEED = BUILDER.defineInRange("stoneAxeSpeed", -3.5, -4.0, 4.0);
        IRON_AXE_DAMAGE = BUILDER.defineInRange("ironAxeDamage", 7.5, 0.0, 20.0);
        IRON_AXE_SPEED = BUILDER.defineInRange("ironAxeSpeed", -3.4, -4.0, 4.0);
        GOLDEN_AXE_DAMAGE = BUILDER.defineInRange("goldenAxeDamage", 5.0, 0.0, 20.0);
        GOLDEN_AXE_SPEED = BUILDER.defineInRange("goldenAxeSpeed", -2.8, -4.0, 4.0);
        DIAMOND_AXE_DAMAGE = BUILDER.defineInRange("diamondAxeDamage", 8.0, 0.0, 20.0);
        DIAMOND_AXE_SPEED = BUILDER.defineInRange("diamondAxeSpeed", -3.4, -4.0, 4.0);
        NETHERITE_AXE_DAMAGE = BUILDER.defineInRange("netheriteAxeDamage", 8.5, 0.0, 20.0);
        NETHERITE_AXE_SPEED = BUILDER.defineInRange("netheriteAxeSpeed", -3.4, -4.0, 4.0);

        MACE_DAMAGE = BUILDER.defineInRange("maceDamage", 1.0, 0.0, 20.0);
        MACE_REACH = BUILDER.defineInRange("maceReach", 1.85, 0.0, 10.0);
        TRIDENT_DAMAGE = BUILDER.defineInRange("tridentDamage", 8.0, 0.0, 20.0);
        SHIELD_DURABILITY = BUILDER.defineInRange("shieldDurability", 200, 1, 2000);
        BUILDER.pop();

        BUILDER.push("armor");
        LEATHER_CHESTPLATE_ARMOR = BUILDER.defineInRange("leatherChestplateArmor", 1.5, 0.0, 20.0);
        LEATHER_LEGGINGS_ARMOR = BUILDER.defineInRange("leatherLeggingsArmor", 1.0, 0.0, 20.0);
        CHAINMAIL_ARMOR = BUILDER.comment("Chainmail chestplate armor").defineInRange("chainmailArmorChest", 3.0, 0.0, 20.0);
        CHAINMAIL_LEGGINGS_ARMOR = BUILDER.defineInRange("chainmailLeggingsArmor", 2.0, 0.0, 20.0);
        IRON_CHESTPLATE_ARMOR = BUILDER.defineInRange("ironChestplateArmor", 4.0, 0.0, 20.0);
        IRON_LEGGINGS_ARMOR = BUILDER.defineInRange("ironLeggingsArmor", 3.0, 0.0, 20.0);
        GOLDEN_CHESTPLATE_ARMOR = BUILDER.defineInRange("goldenChestplateArmor", 5.0, 0.0, 20.0);
        DIAMOND_HELMET_TOUGHNESS = BUILDER.defineInRange("diamondHelmetToughness", 1.0, 0.0, 20.0);
        DIAMOND_CHESTPLATE_ARMOR = BUILDER.defineInRange("diamondChestplateArmor", 5.0, 0.0, 20.0);
        DIAMOND_CHESTPLATE_TOUGHNESS = BUILDER.defineInRange("diamondChestplateToughness", 1.0, 0.0, 20.0);
        DIAMOND_LEGGINGS_ARMOR = BUILDER.defineInRange("diamondLeggingsArmor", 4.0, 0.0, 20.0);
        DIAMOND_LEGGINGS_TOUGHNESS = BUILDER.defineInRange("diamondLegginsToughness", 1.0, 0.0, 20.0);
        DIAMOND_BOOTS_TOUGHNESS = BUILDER.defineInRange("diamondBootsToughness", 1.0, 0.0, 20.0);
        NETHERITE_HELMET_TOUGHNESS = BUILDER.defineInRange("netheriteHelmetToughness", 2.0, 0.0, 20.0);
        NETHERITE_HELMET_KNOCKBACK_RES = BUILDER.defineInRange("netheriteHelmetKnockbackRes", 0.05, 0.0, 1.0);
        NETHERITE_CHESTPLATE_ARMOR = BUILDER.defineInRange("netheriteChestplateArmor", 6.0, 0.0, 20.0);
        NETHERITE_CHESTPLATE_TOUGHNESS = BUILDER.defineInRange("netheriteChestplateToughness", 2.0, 0.0, 20.0);
        NETHERITE_CHESTPLATE_KNOCKBACK_RES = BUILDER.defineInRange("netheriteChestplateKnockbackRes", 0.05, 0.0, 1.0);
        NETHERITE_LEGGINGS_ARMOR = BUILDER.defineInRange("netheriteLeggingsArmor", 5.0, 0.0, 20.0);
        NETHERITE_LEGGINGS_TOUGHNESS = BUILDER.defineInRange("netheriteLegginsToughness", 2.0, 0.0, 20.0);
        NETHERITE_LEGGINGS_KNOCKBACK_RES = BUILDER.defineInRange("netheriteLeggingsKnockbackRes", 0.05, 0.0, 1.0);
        NETHERITE_BOOTS_TOUGHNESS = BUILDER.defineInRange("netheriteBootsToughness", 2.0, 0.0, 20.0);
        NETHERITE_BOOTS_KNOCKBACK_RES = BUILDER.defineInRange("netheriteBootsKnockbackRes", 0.05, 0.0, 1.0);
        TURTLE_HELMET_ARMOR = BUILDER.defineInRange("turtleHelmetArmor", 3.0, 0.0, 20.0);
        TURTLE_HELMET_TOUGHNESS = BUILDER.defineInRange("turtleHelmetToughness", 1.0, 0.0, 20.0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
