package com.yourmod.bettervanilla;

import com.yourmod.bettervanilla.config.BetterVanillaConfig;
import com.yourmod.bettervanilla.events.*;
import com.yourmod.bettervanilla.item.WeaponAttributeModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("bettervanilla")
public class BetterVanillaMod {
    public static final String MOD_ID = "bettervanilla";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public BetterVanillaMod(IEventBus modBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, BetterVanillaConfig.SPEC);

        modBus.addListener(this::commonSetup);

        // Register event handlers
        NeoForge.EVENT_BUS.register(new VillagerTradeEvents());
        NeoForge.EVENT_BUS.register(new MendingBookEvents());
        NeoForge.EVENT_BUS.register(new FoodSaturationEvents());
        NeoForge.EVENT_BUS.register(new RaidEvents());
        NeoForge.EVENT_BUS.register(new SlimeEvents());
        NeoForge.EVENT_BUS.register(new VillagerCombatEvents());
        NeoForge.EVENT_BUS.register(new RaiderDoorEvents());
        NeoForge.EVENT_BUS.register(new PhantomDeathEvents());
        NeoForge.EVENT_BUS.register(new WitherDeathEvents());
        NeoForge.EVENT_BUS.register(new PiglinDamageEvents());
        NeoForge.EVENT_BUS.register(new KillerRabbitEvents());
        NeoForge.EVENT_BUS.register(new DarkMineEvents());
        NeoForge.EVENT_BUS.register(new AxeDebuffEvents());
        NeoForge.EVENT_BUS.register(new ShieldCreeperEvents());
        NeoForge.EVENT_BUS.register(new GoldenAppleLeafEvents());
        NeoForge.EVENT_BUS.register(new WeaponDamageEvents());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WeaponAttributeModifier.applyWeaponChanges();
            LOGGER.info("Better Vanilla mod initialized!");
        });
    }
}
