package com.alexthw.ars_morph.identity.variant;

import com.hollingsworth.arsnouveau.common.entity.EntityDrygmy;
import net.Gabou.identity2.api.variant.IdentityVariantAdapter;
import net.Gabou.identity2.identity.IdentityVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public final class DrygmyVariantAdapter implements IdentityVariantAdapter {

	@Override
	public CompoundTag extractVariantData(LivingEntity entity) {
		CompoundTag tag = new CompoundTag();
		if (entity instanceof EntityDrygmy d) {
			tag.putString("color", d.getColor());
		}
		return tag;
	}

	@Override
	public void applyVariantData(Entity entity, CompoundTag variantNbt) {
		if (!(entity instanceof EntityDrygmy d)) return;
		if (variantNbt.contains("color")) d.getEntityData().set(EntityDrygmy.COLOR, variantNbt.getString("color"));
	}

	@Override
	public List<IdentityVariant> discoverVariants(EntityType<?> type, Level level) {
		List<IdentityVariant> out = new ArrayList<>();
		for (String color : EntityDrygmy.COLORS) {
			CompoundTag tag = new CompoundTag();
			tag.putString("color", color);
			out.add(new IdentityVariant(EntityType.getKey(type), "Drygmy " + color, tag));
		}
		return out;
	}
}


