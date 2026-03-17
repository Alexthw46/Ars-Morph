package com.alexthw.ars_morph.identity.ability;

import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGrow;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

public class WhirlisprigAbility extends MorphBoundAbility<Whirlisprig> {

    final ParticleColor color = new ParticleColor(50, 250, 55);
    final Spell spell = new Spell(EffectGrow.INSTANCE, AugmentAOE.INSTANCE, AugmentAOE.INSTANCE, AugmentAOE.INSTANCE);

    public WhirlisprigAbility() {
        super(Whirlisprig.class);
    }

    @Override
    protected void use(Player player, Whirlisprig w) {
        Level level = player.level();
        EntitySpellResolver resolver = new EntitySpellResolver(new SpellContext(level, spell, player, new LivingCaster(player)).withColors(color));
        if (resolver.postEvent().isCanceled()) {
            return;
        }
        resolver.onResolveEffect(level, new BlockHitResult(player.position(), Direction.DOWN, player.blockPosition().below(), true));
    }

    @Override
    protected void passiveTick(Player player, Whirlisprig morph, boolean used) {
        if (!player.level().isClientSide()) {
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 2, true, false));
        }
    }

}
