package com.alexthw.ars_morph.datagen;

import com.alexthw.ars_morph.ArsMorph;
import com.alexthw.ars_morph.glyphs.EffectMorph;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.datagen.GlyphRecipeProvider;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;

public class ArsProviders {

    static String root = ArsMorph.MODID;

    public static class GlyphProvider extends GlyphRecipeProvider {

        public GlyphProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void collectJsons(CachedOutput cache) {

            Path output = this.generator.getPackOutput().getOutputFolder();

            recipes.add(get(EffectMorph.INSTANCE).withItem(Items.DIRT));

            for (GlyphRecipe recipe : recipes) {
                Path path = getScribeGlyphPath(output, recipe.output.getItem());
                saveStable(cache, GlyphRecipe.CODEC.encodeStart(JsonOps.INSTANCE, recipe).getOrThrow(), path);
            }
        }

        protected static Path getScribeGlyphPath(Path pathIn, Item glyph) {
            return pathIn.resolve("data/" + root + "/recipe/" + getRegistryName(glyph).getPath() + ".json");
        }

        @Override
        public @NotNull String getName() {
            return "Ars Morph Glyph Recipes";
        }
    }

}
