package com.alexthw.ars_morph.glyphs;

import com.alexthw.ars_morph.identity.IdentityReg;
import com.hollingsworth.arsnouveau.api.spell.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import java.util.Set;

import static com.alexthw.ars_morph.ArsMorph.prefix;

public class MorphEffect extends AbstractEffect {

    public static MorphEffect INSTANCE = new MorphEffect(prefix("glyph_morph"), "Morph");

    public MorphEffect(ResourceLocation tag, String description) {
        super(tag, description);
    }

    @Override
    public int getDefaultManaCost() {
        return 100;
    }


    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        super.onResolveEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver);
        if (rayTraceResult.getEntity() instanceof LivingEntity living && shooter instanceof ServerPlayer player)
            IdentityReg.morphInto(world, player, living);
    }

    @Nonnull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return getSummonAugments();
    }
}
