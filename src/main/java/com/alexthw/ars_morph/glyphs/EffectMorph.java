package com.alexthw.ars_morph.glyphs;

import com.hollingsworth.arsnouveau.api.entity.IDecoratable;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarEntity;
import com.hollingsworth.arsnouveau.common.items.MobJarItem;
import draylar.identity.api.PlayerIdentity;
import draylar.identity.api.variant.IdentityType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
            IdentityType<?> type = null;
            LivingEntity morph = null;
            if (shooter.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof MobJarItem && MobJarItem.fromItem(shooter.getItemInHand(InteractionHand.OFF_HAND), world) instanceof LivingEntity toMorph) {
                morph = toMorph;
                type = IdentityType.from(toMorph);
            }
            if (living == shooter) {
                PlayerIdentity.updateIdentity(player, type, morph);
                world.sendParticles(ParticleTypes.LARGE_SMOKE, shooter.getX(), shooter.getY() + 0.5, shooter.getZ(), 30,
                        ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), 0.3);
            } else if (!(living instanceof Player) && living.getMaxHealth() < GENERIC_INT.get()) {
                type = IdentityType.from(living);
                if (type != null) {
                    morph = type.create(world);
                    if (morph instanceof IDecoratable toDeco && living instanceof IDecoratable fromDeco) {
                        toDeco.setCosmeticItem(fromDeco.getCosmeticItem());
                    }
                    if (PlayerIdentity.updateIdentity(player, type, morph)) {
                        world.sendParticles(ParticleTypes.LARGE_SMOKE, shooter.getX(), shooter.getY() + 0.5, shooter.getZ(), 30,
                                ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), 0.3);
                        //if (isTimeLimited.get())
                        //    ((IPotionEffect) this).applyConfigPotion(living, IdentityReg.MORPH.get(), spellStats, false);
                    }
                }
            } else if (living instanceof ServerPlayer otherPlayer) {
                if (PlayerIdentity.updateIdentity(otherPlayer, type, morph)) {
                    world.sendParticles(ParticleTypes.LARGE_SMOKE, shooter.getX(), shooter.getY() + 0.5, shooter.getZ(), 30,
                            ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), 0.3);
                    //if (isTimeLimited.get())
                    //    ((IPotionEffect) this).applyConfigPotion(living, IdentityReg.MORPH.get(), spellStats, false);
                }
            }
        }

    }

    @Override
    public void buildConfig(ModConfigSpec.Builder builder) {
        super.buildConfig(builder);
        //isTimeLimited = builder.comment("Enable a timer on the resize effects. Caster will return to original self when potion effect is removed.").define("limitedMorphTime", false);
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
