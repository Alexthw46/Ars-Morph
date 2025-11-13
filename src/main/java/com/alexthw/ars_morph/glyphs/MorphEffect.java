package com.alexthw.ars_morph.glyphs;

import com.hollingsworth.arsnouveau.api.spell.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

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
    public void onResolve(HitResult rayTraceResult, Level world, @Nonnull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        super.onResolve(rayTraceResult, world, shooter, spellStats, spellContext, resolver);

    }


    @Nonnull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return getSummonAugments();
    }
}
