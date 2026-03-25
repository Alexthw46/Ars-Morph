package com.alexthw.ars_morph.identity.variant;

import alexthw.ars_elemental.common.entity.FlashjackEntity;
import net.Gabou.identity2.api.variant.IdentityVariantAdapter;
import net.Gabou.identity2.identity.IdentityVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FlashjackVariantAdapter implements IdentityVariantAdapter {

    @Override
    public CompoundTag extractVariantData(LivingEntity entity) {
        CompoundTag tag = new CompoundTag();
        if (entity instanceof FlashjackEntity f) {
            tag.putString("Variant", f.getColor());
        }
        return tag;
    }

    @Override
    public void applyVariantData(Entity entity, CompoundTag variantNbt) {
        if (!(entity instanceof FlashjackEntity f)) return;
        if (variantNbt.contains("Variant")) f.setColor(variantNbt.getString("Variant"));
    }

    enum Variants {
        FLASHJACK,
        BLUEJAY,
        FLAPJACK;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }

    }

    @Override
    public List<IdentityVariant> discoverVariants(EntityType<?> type, Level level) {
        List<IdentityVariant> out = new ArrayList<>();
        for (Variants v : Variants.values()) {
            CompoundTag tag = new CompoundTag();
            tag.putString("Variant", v.toString());
            out.add(new IdentityVariant(EntityType.getKey(type), "Flashjack " + v, tag));
        }
        return out;
    }
}

