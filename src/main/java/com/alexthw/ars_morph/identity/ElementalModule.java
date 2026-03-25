package com.alexthw.ars_morph.identity;

import alexthw.ars_elemental.registry.ModEntities;
import com.alexthw.ars_morph.identity.ability.FirenandoAbility;
import com.alexthw.ars_morph.identity.variant.FirenandoVariantAdapter;
import com.alexthw.ars_morph.identity.variant.FlashjackVariantAdapter;
import com.alexthw.ars_morph.identity.variant.MermaidVariantAdapter;
import net.Gabou.identity2.api.IdentityApi;
import net.neoforged.fml.ModList;

public final class ElementalModule {

    private ElementalModule() {
    }

    public static void initAbilities() {
        if (!ModList.get().isLoaded("ars_elemental")) return;

        IdentityReg.registerAbility(ModEntities.FIRENANDO_ENTITY.getId(), new FirenandoAbility());
    }

    public static void initVariants() {
        // Register variant adapters for elemental entities
        IdentityApi.registerVariantAdapter(ModEntities.FIRENANDO_ENTITY.get(), new FirenandoVariantAdapter());
        IdentityApi.registerVariantAdapter(ModEntities.SIREN_ENTITY.get(), new MermaidVariantAdapter());
        IdentityApi.registerVariantAdapter(ModEntities.FLASHJACK_ENTITY.get(), new FlashjackVariantAdapter());
    }

}
