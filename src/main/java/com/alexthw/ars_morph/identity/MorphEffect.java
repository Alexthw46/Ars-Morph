package com.alexthw.ars_morph.identity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class MorphEffect extends MobEffect {

    protected MorphEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public void onMobRemoved(@NotNull LivingEntity livingEntity, int amplifier, Entity.@NotNull RemovalReason reason) {
        if (livingEntity instanceof ServerPlayer player)
            IdentityReg.morphInto(player.level(), player,null);
        super.onMobRemoved(livingEntity, amplifier, reason);
    }
}
