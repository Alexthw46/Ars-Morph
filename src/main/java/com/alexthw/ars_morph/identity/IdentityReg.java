package com.alexthw.ars_morph.identity;

import com.alexthw.ars_morph.identity.ability.StarbuncleAbility;
import com.alexthw.ars_morph.identity.ability.WealdWalkerAbility;
import com.alexthw.ars_morph.identity.ability.WhirlisprigAbility;
import com.alexthw.ars_morph.identity.ability.WildenHunterAbility;
import com.alexthw.ars_morph.identity.ability.WildenStalkerAbility;
import com.alexthw.ars_morph.identity.ability.WixieAbility;
import com.alexthw.ars_morph.identity.variant.BookwyrmVariantAdapter;
import com.alexthw.ars_morph.identity.variant.DrygmyVariantAdapter;
import com.alexthw.ars_morph.identity.variant.StarbuncleVariantAdapter;
import com.alexthw.ars_morph.identity.variant.WhirlisprigVariantAdapter;
import com.alexthw.ars_morph.identity.variant.WixieVariantAdapter;
import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import com.hollingsworth.arsnouveau.setup.registry.ModEntities;
import net.Gabou.identity2.api.IdentityApi;
import net.Gabou.identity2.api.ability.BuiltinIdentityAbility;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Central registration helper for Identity2 integrations.
 */
public final class IdentityReg {

    private IdentityReg() {
    }

    private static final Logger LOGGER = LogManager.getLogger();

    public static void preInit() {
        // reserved for future use
    }

    public static void postInit() {
        // Register during common setup
        registerAbilities();
        registerVariantAdapters();
        registerTickHandlers();
    }

    private static void registerVariantAdapters() {
        IdentityApi.registerVariantAdapter(ModEntities.STARBUNCLE_TYPE.get(), new StarbuncleVariantAdapter());
        IdentityApi.registerVariantAdapter(ModEntities.WHIRLISPRIG_TYPE.get(), new WhirlisprigVariantAdapter());
        IdentityApi.registerVariantAdapter(ModEntities.ENTITY_WIXIE_TYPE.get(), new WixieVariantAdapter());
        IdentityApi.registerVariantAdapter(ModEntities.ENTITY_DRYGMY.get(), new DrygmyVariantAdapter());
        IdentityApi.registerVariantAdapter(ModEntities.ENTITY_BOOKWYRM_TYPE.get(), new BookwyrmVariantAdapter());

        // ElementalModule - Ars Elemental variant adapters
        if (ModList.get().isLoaded("ars_elemental")) ElementalModule.initVariants();
    }

    private static void registerAbilities() {
        // Register by EntityType where possible - natural builtin id will be the entity type id
        IdentityApi.registerBuiltinAbility(ResourceLocation.fromNamespaceAndPath("ars_nouveau", "weald_walker"), new WealdWalkerAbility());

        IdentityApi.registerBuiltinAbility(ModEntities.WILDEN_HUNTER.get(), new WildenHunterAbility());
        IdentityApi.registerBuiltinAbility(ModEntities.WILDEN_STALKER.get(), new WildenStalkerAbility());
        IdentityApi.registerBuiltinAbility(ModEntities.STARBUNCLE_TYPE.get(), new StarbuncleAbility());
        IdentityApi.registerBuiltinAbility(ModEntities.WHIRLISPRIG_TYPE.get(), new WhirlisprigAbility());
        IdentityApi.registerBuiltinAbility(ModEntities.ENTITY_WIXIE_TYPE.get(), new WixieAbility());
        // ElementalModule - register additional abilities from Ars Elemental mobs
        if (ModList.get().isLoaded("ars_elemental")) ElementalModule.initAbilities();
    }

    private static void registerTickHandlers() {
        // WildenStalker: mirror flying state from host to morph (server-side)
        IdentityApi.registerMorphTickHandler(ModEntities.WILDEN_STALKER.get(), (host, currentMorph) -> {
            if (host.level().isClientSide()) return;
            if (!(host instanceof ServerPlayer serverPlayer)) return; // must be server player to sync
            try {
                boolean flying = !serverPlayer.onGround() && !serverPlayer.isInWater();
                if (flying) flying = serverPlayer.level().getBlockState(serverPlayer.getOnPos()).isAir();
                if (currentMorph instanceof WildenStalker stalker) {
                        stalker.setFlying(flying);
                }
                IdentityApi.syncBoolean(serverPlayer, "isFlying", flying);
            } catch (Throwable t) {
                LOGGER.error("Error in WildenStalker tick handler", t);
            }
        });
    }

    public static void registerAbility(ResourceLocation id, BuiltinIdentityAbility ability) {
        IdentityApi.registerBuiltinAbility(id, ability);
    }
}
