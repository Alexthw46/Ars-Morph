package com.alexthw.ars_morph.identity;

import alexthw.ars_elemental.registry.ModEntities;
import com.alexthw.ars_morph.identity.ability.FirenandoAbility;
import com.alexthw.ars_morph.identity.ability.WealdWalkerAbility;
import com.alexthw.ars_morph.identity.variant.FirenandoVariantAdapter;
import com.alexthw.ars_morph.identity.variant.MermaidVariantAdapter;
import net.Gabou.identity2.api.IdentityApi;
import net.neoforged.fml.ModList;

public final class ElementalModule {

    private ElementalModule() {
    }

    public static void initAbilities() {
        if (!ModList.get().isLoaded("ars_elemental")) return;

        // Register abilities exposed by ars_elemental. Use IdentityReg.registerAbility if you prefer ResourceLocation ids.
        IdentityReg.registerAbility(ModEntities.FLASHING_WEALD_WALKER.getId(), new WealdWalkerAbility());
        IdentityReg.registerAbility(ModEntities.FIRENANDO_ENTITY.getId(), new FirenandoAbility());
    }

    public static void initVariants() {
        // Register variant adapters for elemental entities
        IdentityApi.registerVariantAdapter(ModEntities.FIRENANDO_ENTITY.get(), new FirenandoVariantAdapter());
        IdentityApi.registerVariantAdapter(ModEntities.SIREN_ENTITY.get(), new MermaidVariantAdapter());
    }

}
