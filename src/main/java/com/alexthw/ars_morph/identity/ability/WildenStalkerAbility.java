package com.alexthw.ars_morph.identity.ability;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGlide;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLaunch;
import com.hollingsworth.arsnouveau.common.spell.method.MethodSelf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class WildenStalkerAbility extends MorphBoundAbility<WildenStalker> {

    static final Spell summon = new Spell().add(MethodSelf.INSTANCE)
            .add(EffectLaunch.INSTANCE, 2)
            .add(EffectGlide.INSTANCE)
            .add(AugmentDurationDown.INSTANCE, 1);

    public WildenStalkerAbility() {
        super(WildenStalker.class);
    }

    @Override
    protected void use(Player player, WildenStalker wildenHunter) {
        Level level = player.level();
        level.playSound(null, player.blockPosition(), SoundEvents.BAT_TAKEOFF, SoundSource.HOSTILE, 1.0f, 0.3f);

        SpellResolver resolver = new SpellResolver(new SpellContext(level, summon, player, new LivingCaster(player)));
        if (!resolver.postEvent().isCanceled()) {
            resolver.onResolveEffect(level, new EntityHitResult(player));
        }
    }

}
