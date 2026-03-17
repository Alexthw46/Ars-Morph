package com.alexthw.ars_morph.identity.variant;

import com.hollingsworth.arsnouveau.common.entity.EntityBookwyrm;
import net.Gabou.identity2.api.variant.IdentityVariantAdapter;
import net.Gabou.identity2.identity.IdentityVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public final class BookwyrmVariantAdapter implements IdentityVariantAdapter {

    @Override
    public CompoundTag extractVariantData(LivingEntity entity) {
        CompoundTag tag = new CompoundTag();
        if (entity instanceof EntityBookwyrm b) {
            tag.putString("color", b.getColor());
        }
        return tag;
    }

    @Override
    public void applyVariantData(Entity entity, CompoundTag variantNbt) {
        if (!(entity instanceof EntityBookwyrm b)) return;
        if (variantNbt.contains("color")) b.setColor(variantNbt.getString("color"));
    }

    @Override
    public List<IdentityVariant> discoverVariants(EntityType<?> type, Level level) {
        List<IdentityVariant> out = new ArrayList<>();
        for (String color : EntityBookwyrm.COLORS) {
            CompoundTag tag = new CompoundTag();
            tag.putString("color", color);
            out.add(new IdentityVariant(EntityType.getKey(type), "Bookwyrm " + color, tag));
        }
        return out;
    }
}

