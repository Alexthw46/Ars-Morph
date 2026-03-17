package com.alexthw.ars_morph;

import com.alexthw.ars_morph.identity.IdentityReg;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsMorph.MODID)
public class ArsMorph {
    public static final String MODID = "ars_morph";

    private static final Logger LOGGER = LogManager.getLogger();

    public ArsMorph(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, MorphConfig.COMMON_SPEC);

        ArsNouveauRegistry.registerGlyphs();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);
        IdentityReg.preInit();
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
        IdentityReg.postInit();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

}
