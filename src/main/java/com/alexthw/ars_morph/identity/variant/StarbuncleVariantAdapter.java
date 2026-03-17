package com.alexthw.ars_morph.identity.variant;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import net.Gabou.identity2.api.variant.IdentityVariantAdapter;
import net.Gabou.identity2.identity.IdentityVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public final class StarbuncleVariantAdapter implements IdentityVariantAdapter {

    @Override
    public CompoundTag extractVariantData(LivingEntity entity) {
        CompoundTag tag = new CompoundTag();
        if (entity instanceof Starbuncle sb) {
            tag.putString("color", sb.getColor());
            tag.putBoolean("tamed", sb.isTamed());
        }
        return tag;
    }

    @Override
    public void applyVariantData(Entity entity, CompoundTag variantNbt) {
        if (!(entity instanceof Starbuncle sb)) return;
        if (variantNbt.contains("color")) sb.setColor(variantNbt.getString("color"));
        if (variantNbt.contains("tamed")) sb.setTamed(variantNbt.getBoolean("tamed"));
    }

    @Override
    public List<IdentityVariant> discoverVariants(EntityType<?> type, Level level) {
        List<IdentityVariant> out = new ArrayList<>();
        for (String color : Starbuncle.carbyColors) {
            CompoundTag tag = new CompoundTag();
            tag.putString("color", color);
            tag.putBoolean("tamed", false);
            out.add(new IdentityVariant(EntityType.getKey(type), "Starbuncle " + color, tag));

            CompoundTag tagTamed = new CompoundTag();
            tagTamed.putString("color", color);
            tagTamed.putBoolean("tamed", true);
            out.add(new IdentityVariant(EntityType.getKey(type), "Tamed Starbuncle " + color, tagTamed));
        }
        return out;
    }
}

