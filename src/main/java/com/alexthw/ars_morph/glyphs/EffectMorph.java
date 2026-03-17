package com.alexthw.ars_morph.glyphs;

import com.hollingsworth.arsnouveau.api.entity.IDecoratable;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarEntity;
import com.hollingsworth.arsnouveau.common.items.MobJarItem;
import net.Gabou.identity2.identity.IdentityProgression;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static com.alexthw.ars_morph.ArsMorph.prefix;

public class EffectMorph extends AbstractEffect {

    public static EffectMorph INSTANCE = new EffectMorph(prefix("glyph_morph"), "Morph");

    public EffectMorph(ResourceLocation tag, String description) {
        super(tag, description);
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level level, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {

        if (level instanceof ServerLevel world && shooter instanceof ServerPlayer player && isRealPlayer(shooter) && rayTraceResult.getEntity() instanceof LivingEntity living) {
            if (living instanceof FamiliarEntity) return;

            EntityType<?> type = shooter.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof MobJarItem && MobJarItem.fromItem(shooter.getItemInHand(InteractionHand.OFF_HAND), world) instanceof LivingEntity toMorph ? toMorph.getType() : living.getType();

            if (type == living.getType() && shooter.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof SpawnEggItem egg){
                type = egg.getType(shooter.getItemInHand(InteractionHand.OFF_HAND));
            }

            // Self-morph without a mob jar just clears the morph
            if (living instanceof ServerPlayer targetPlayer && type == EntityType.PLAYER) {
                IdentityProgression.clearMorph(targetPlayer);
                world.sendParticles(ParticleTypes.LARGE_SMOKE, targetPlayer.getX(), targetPlayer.getY() + 0.5, targetPlayer.getZ(), 30,
                        ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), 0.3);
            } else {
                // There is a target mob, morph the caster or the hit player into it.
                LivingEntity morph = (LivingEntity) type.create(world);
                if (morph == null) return; // Shouldn't happen but just in case
                if (morph.getMaxHealth() < GENERIC_INT.get()) {

                    // Copy cosmetic item from the target if possible
                    if (morph instanceof IDecoratable toDeco && living instanceof IDecoratable fromDeco) {
                        toDeco.setCosmeticItem(fromDeco.getCosmeticItem());
                    }
                    ServerPlayer toMorph = living instanceof ServerPlayer target ? target : player;
                    // Morph into the entity only if it has less max hp than the config value.
                    if (IdentityProgression.morph(toMorph, BuiltInRegistries.ENTITY_TYPE.getKey(type), morph.serializeNBT(morph.registryAccess()))) {
                        world.sendParticles(ParticleTypes.LARGE_SMOKE, toMorph.getX(), toMorph.getY() + 0.5, toMorph.getZ(), 30,
                                ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), 0.3);
                        //if (isTimeLimited.get())
                        //    ((IPotionEffect) this).applyConfigPotion(living, IdentityReg.MORPH.get(), spellStats, false);
                    }
                }
            }
        }

    }

    @Override
    public void buildConfig(ModConfigSpec.Builder builder) {
        super.buildConfig(builder);
        //isTimeLimited = builder.comment("Enable a timer on the morph effect. Caster will return to original self when potion effect is removed.").define("limitedMorphTime", false);
        //addPotionConfig(builder, 120);
        //addExtendTimeConfig(builder, 60);
        GENERIC_INT = builder.comment("Morph will only allow you to transform is the target have less maximum hp than this value.").defineInRange("max_hp_morph", 100, 20, Integer.MAX_VALUE);
    }

    @Override
    public int getDefaultManaCost() {
        return 200;
    }

    @Override
    public SpellTier defaultTier() {
        return SpellTier.TWO;
    }

    @Override
    protected @NotNull Set<SpellSchool> getSchools() {
        return Set.of(SpellSchools.CONJURATION);
    }

    @Override
    protected @NotNull Set<AbstractAugment> getCompatibleAugments() {
        return Set.of();
    }
}
