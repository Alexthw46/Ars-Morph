package com.alexthw.ars_morph.identity.ability;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class StarbuncleAbility extends MorphBoundAbility<Starbuncle> {

    public StarbuncleAbility() {
        super(Starbuncle.class);
    }

    @Override
    protected void use(Player player, Starbuncle morph) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3000, 1, false, false, false));
    }

}
