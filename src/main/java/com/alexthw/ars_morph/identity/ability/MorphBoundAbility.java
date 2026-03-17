package com.alexthw.ars_morph.identity.ability;

import net.Gabou.identity2.api.IdentityApi;
import net.Gabou.identity2.api.ability.BuiltinIdentityAbility;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Generic adapter: maps a BuiltinIdentityAbility invocation to a typed morph-specific callback.
 * Subclass and implement {@link #use(Player, LivingEntity)}.
 */
public abstract class MorphBoundAbility<T extends LivingEntity> implements BuiltinIdentityAbility {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Class<T> morphClass;

    protected MorphBoundAbility(Class<T> morphClass) {
        this.morphClass = morphClass;
    }

    @Override
    public final void execute(Entity host) {
        if (!(host instanceof Player player)) return;
        Entity current = IdentityApi.getCurrentMorph(player);
        if (current == null) return;
        if (!morphClass.isInstance(current)) return;
        T morph = morphClass.cast(current);
        try {
            use(player, morph);
        } catch (Throwable t) {
            LOGGER.error("Error executing morph ability for {}", morphClass, t);
        }
    }

    /**
     * Called when the primary ability is used and the current morph is of the expected type.
     */
    protected abstract void use(Player player, T morph);

    @Override
    public void passiveTick(Entity host, boolean used) {
        if (!(host instanceof Player player)) return;
        Entity current = IdentityApi.getCurrentMorph(player);
        if (current == null) return;
        if (!morphClass.isInstance(current)) return;
        T morph = morphClass.cast(current);
        try {
            passiveTick(player, morph, used);
        } catch (Throwable t) {
            LOGGER.error("Error in passiveTick for {}", morphClass, t);
        }
    }

    /**
     * Optional passive per-tick callback. Called when the passive ability path executes and
     * the current morph is of the expected type.
     */
    protected void passiveTick(Player player, T morph, boolean used) {
        // default no-op
    }

    // default implementations from BuiltinIdentityAbility are sufficient for the rest
}

