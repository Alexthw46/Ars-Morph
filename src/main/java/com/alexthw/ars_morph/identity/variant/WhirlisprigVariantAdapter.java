package com.alexthw.ars_morph.identity.variant;

import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import net.Gabou.identity2.api.variant.IdentityVariantAdapter;
import net.Gabou.identity2.identity.IdentityVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public final class WhirlisprigVariantAdapter implements IdentityVariantAdapter {

	private static final String[] SEASONS = new String[] {"summer", "winter", "autumn", "spring"};

	@Override
	public CompoundTag extractVariantData(LivingEntity entity) {
		CompoundTag tag = new CompoundTag();
		if (entity instanceof Whirlisprig wp) {
			tag.putString("color", wp.getColor());
		}
		return tag;
	}

	@Override
	public void applyVariantData(Entity entity, CompoundTag variantNbt) {
		if (!(entity instanceof Whirlisprig wp)) return;
		if (variantNbt.contains("color")) wp.getEntityData().set(Whirlisprig.COLOR, variantNbt.getString("color"));
	}

	@Override
	public List<IdentityVariant> discoverVariants(EntityType<?> type, Level level) {
		List<IdentityVariant> out = new ArrayList<>();
		for (String s : SEASONS) {
			CompoundTag tag = new CompoundTag();
			tag.putString("color", s);
			out.add(new IdentityVariant(EntityType.getKey(type), "Whirlisprig " + s, tag));
		}
		return out;
	}
}


