package com.alexthw.ars_morph.identity.ability;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.registry.RitualRegistry;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class WealdWalkerAbility extends MorphBoundAbility<WealdWalker> {

    public WealdWalkerAbility() {
        super(WealdWalker.class);
    }

    @Override
    protected void use(Player player, WealdWalker morph) {
        var level = player.level();
        EntitySpellResolver resolver = new EntitySpellResolver(new SpellContext(level, morph.spell, player, new LivingCaster(player)).withColors(morph.color));
        if (resolver.postEvent().isCanceled()) {
            return;
        }
        EntityProjectileSpell projectileSpell = new EntityProjectileSpell(level, resolver);
        projectileSpell.shoot(player, player.getXRot(), player.getYRot(), 0.0F, 1.0f, 0.8f);
        level.addFreshEntity(projectileSpell);
    }

    public RitualTablet getRitualItem(String name) {
        return RitualRegistry.getRitualItemMap().get(ResourceLocation.fromNamespaceAndPath(ArsNouveau.MODID, name));
    }

}
