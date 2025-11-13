package com.alexthw.ars_morph.identity;

import com.alexthw.ars_morph.identity.ability.*;
import com.alexthw.ars_morph.identity.rendering.ColorVariantProvider;
import com.alexthw.ars_morph.identity.rendering.StarbuncleTypeProvider;
import com.alexthw.ars_morph.identity.tick_handlers.StalkerTickHandler;
import com.alexthw.ars_morph.identity.tick_handlers.WhirlSprigTickHandler;
import com.hollingsworth.arsnouveau.common.entity.*;
import com.hollingsworth.arsnouveau.setup.registry.ModEntities;
import draylar.identity.ability.AbilityRegistry;
import draylar.identity.api.IdentityTickHandlers;
import draylar.identity.api.PlayerIdentity;
import draylar.identity.api.variant.IdentityType;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;

import java.util.Map;


public class IdentityReg {

    //public static DeferredHolder<MobEffect, MobEffect> MORPH;

    public static void preInit() {
        //MORPH = EFFECTS.register("morph", MorphEffect::new);
        NeoForge.EVENT_BUS.register(IdentityReg.class);
    }

    public static void postInit() {
        Map<EntityType<? extends LivingEntity>, TypeProvider<?>> variants = ObfuscationReflectionHelper.getPrivateValue(IdentityType.class, new IdentityType<>(null, 0), "VARIANT_BY_TYPE");
        initAbilities();
        if (variants != null) initVariants(variants);
    }

    private static void initVariants(Map<EntityType<? extends LivingEntity>, TypeProvider<?>> variants) {

        variants.put(ModEntities.STARBUNCLE_TYPE.get(), new StarbuncleTypeProvider());

        variants.put(ModEntities.WHIRLISPRIG_TYPE.get(), new ColorVariantProvider<Whirlisprig>() {
            @Override
            protected void setColor(Whirlisprig whirlisprig, String color) {
                whirlisprig.getEntityData().set(Whirlisprig.COLOR, color);
            }

            @Override
            public int getRange() {
                return 3;
            }
        });
        variants.put(ModEntities.ENTITY_DRYGMY.get(), new ColorVariantProvider<EntityDrygmy>() {
            @Override
            protected void setColor(EntityDrygmy drygmy, String color) {
                drygmy.getEntityData().set(EntityDrygmy.COLOR, color);
            }

            @Override
            public int getRange() {
                return EntityDrygmy.COLORS.length - 1;
            }
        });
        variants.put(ModEntities.ENTITY_BOOKWYRM_TYPE.get(), new ColorVariantProvider<EntityBookwyrm>() {

            @Override
            protected void setColor(EntityBookwyrm bookwyrm, String color) {
                bookwyrm.setColor(color);
            }

            @Override
            public int getRange() {
                return EntityBookwyrm.COLORS.length - 1;
            }

        });
        variants.put(ModEntities.ENTITY_WIXIE_TYPE.get(), new ColorVariantProvider<EntityWixie>() {

            @Override
            protected void setColor(EntityWixie entityWixie, String color) {
                entityWixie.getEntityData().set(EntityWixie.COLOR, color);
            }

            @Override
            public int getRange() {
                return EntityWixie.COLORS.length - 1;
            }
        });

        if (ModList.get().isLoaded("ars_elemental")) ElementalModule.variants(variants);

    }


    public static void initAbilities() {
        AbilityRegistry.register(ModEntities.ENTITY_BLAZING_WEALD.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_CASCADING_WEALD.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_FLOURISHING_WEALD.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_VEXING_WEALD.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.WILDEN_HUNTER.get(), new WildenHunterAbility());
        AbilityRegistry.register(ModEntities.WILDEN_STALKER.get(), new WildenStalkerAbility());

        AbilityRegistry.register(ModEntities.STARBUNCLE_TYPE.get(), new StarbuncleAbility<>());
        AbilityRegistry.register(ModEntities.WHIRLISPRIG_TYPE.get(), new WhirlisprigAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_WIXIE_TYPE.get(), new WixieAbility());

        IdentityTickHandlers.register(ModEntities.WHIRLISPRIG_TYPE.get(), new WhirlSprigTickHandler());
        IdentityTickHandlers.register(ModEntities.WILDEN_STALKER.get(), new StalkerTickHandler());

        if (ModList.get().isLoaded("ars_elemental")) ElementalModule.initAbilities();
    }

    @SubscribeEvent
    public static void onLivingAttack(EntityInvulnerabilityCheckEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource() == player.damageSources().sweetBerryBush()) {
            if (PlayerIdentity.getIdentity(player) instanceof Starbuncle) event.setInvulnerable(true);
        }

    }

}