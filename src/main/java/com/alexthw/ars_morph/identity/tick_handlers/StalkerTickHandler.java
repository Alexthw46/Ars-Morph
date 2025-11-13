package com.alexthw.ars_morph.identity.tick_handlers;

import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import draylar.identity.api.IdentityTickHandler;
import net.minecraft.world.entity.player.Player;

public class StalkerTickHandler implements IdentityTickHandler<WildenStalker> {

    @Override
    public void tick(Player player, WildenStalker stalker) {
        if (player.level().isClientSide()) {
            if (player.isFallFlying() || player.onGround() || player.isInWater())
                stalker.setFlying(!player.onGround() && !player.isInWater());
        }
    }

}
