package com.alexthw.ars_morph.identity.ability;

import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.setup.registry.ModPotions;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WixieAbility extends MorphBoundAbility<EntityWixie> {

    public static final ArrayList<Holder<MobEffect>> goodEffectTable = new ArrayList<>(Arrays.asList(
            MobEffects.SATURATION, MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED, MobEffects.DAMAGE_BOOST,
            MobEffects.ABSORPTION, MobEffects.FIRE_RESISTANCE, MobEffects.REGENERATION, MobEffects.DOLPHINS_GRACE,
            ModPotions.MANA_REGEN_EFFECT, ModPotions.DEFENCE_EFFECT
    ));

    public static final ArrayList<Holder<MobEffect>> badEffectTable = new ArrayList<>(Arrays.asList(
            MobEffects.MOVEMENT_SLOWDOWN, MobEffects.WEAKNESS, MobEffects.LEVITATION, MobEffects.POISON,
            MobEffects.CONFUSION, MobEffects.BLINDNESS, MobEffects.DARKNESS, MobEffects.DIG_SLOWDOWN, MobEffects.HARM,
            ModPotions.FREEZING_EFFECT, ModPotions.BLAST_EFFECT, ModPotions.HEX_EFFECT
    ));

    public WixieAbility() {
        super(EntityWixie.class);
    }

    @Override
    protected void use(Player player, EntityWixie entityWixie) {
        Holder<MobEffect> effect = (player.isCrouching() ? goodEffectTable : badEffectTable).get(player.getRandom().nextInt(badEffectTable.size()));

        ThrownPotion thrownpotion = new ThrownPotion(player.level(), player);
        ItemStack stckToThrow = getThrownStack(effect);
        thrownpotion.setItem(stckToThrow);
        thrownpotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
        player.level().addFreshEntity(thrownpotion);
    }

    private ItemStack getThrownStack(Holder<MobEffect> effect) {
        ItemStack splashStack = new ItemStack(Items.SPLASH_POTION);
        splashStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.of(Potions.WATER), Optional.empty(), List.of(new MobEffectInstance(effect, 200, 1))));
        return splashStack;
    }

}
