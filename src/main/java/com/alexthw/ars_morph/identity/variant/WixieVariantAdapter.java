package com.alexthw.ars_morph.identity.variant;

import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import net.Gabou.identity2.api.variant.IdentityVariantAdapter;
import net.Gabou.identity2.identity.IdentityVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public final class WixieVariantAdapter implements IdentityVariantAdapter {

    @Override
    public CompoundTag extractVariantData(LivingEntity entity) {
        CompoundTag tag = new CompoundTag();
        if (entity instanceof EntityWixie w) {
            tag.putString("color", w.getColor());
        }
        return tag;
    }

    @Override
    public void applyVariantData(Entity entity, CompoundTag variantNbt) {
        if (!(entity instanceof EntityWixie w)) return;
        if (variantNbt.contains("color")) w.getEntityData().set(EntityWixie.COLOR, variantNbt.getString("color"));
    }

    @Override
    public List<IdentityVariant> discoverVariants(EntityType<?> type, Level level) {
        List<IdentityVariant> out = new ArrayList<>();
        for (String color : EntityWixie.COLORS) {
            CompoundTag tag = new CompoundTag();
            tag.putString("color", color);
            out.add(new IdentityVariant(EntityType.getKey(type), "Wixie " + color, tag));
        }
        return out;
    }
}

