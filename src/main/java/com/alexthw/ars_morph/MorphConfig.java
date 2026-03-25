package com.alexthw.ars_morph;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MorphConfig {

    public static class Common {

        //Identity
        public static ModConfigSpec.ConfigValue<Integer> FLARE_COOLDOWN;
        public static ModConfigSpec.ConfigValue<Integer> STARBY_COOLDOWN;
        public static ModConfigSpec.ConfigValue<Integer> WW_COOLDOWN;
        public static ModConfigSpec.ConfigValue<Integer> WHIRLI_COOLDOWN;
        public static ModConfigSpec.ConfigValue<Integer> WIL_HUNTER_COOLDOWN;
        public static ModConfigSpec.ConfigValue<Integer> WIL_STALKER_COOLDOWN;
        public static ModConfigSpec.ConfigValue<Integer> WIXIE_COOLDOWN;

        public Common(ModConfigSpec.Builder builder) {

            builder.comment("Identity Abilities").
                    push("IDENTITY MORPHS [DEPRECATED]");

            FLARE_COOLDOWN = builder.comment("cooldown for the active ability of flarecannon").

                    define("flarecannon_cooldown", 120);

            STARBY_COOLDOWN = builder.comment("cooldown for the active ability of starbuncle").

                    define("starby_cooldown", 3600);

            WW_COOLDOWN = builder.comment("cooldown for the active ability of weald walker").

                    define("ww_cooldown", 100);

            WHIRLI_COOLDOWN = builder.comment("cooldown for the active ability of whirlisprig").

                    define("whirli_cooldown", 400);

            WIL_HUNTER_COOLDOWN = builder.comment("cooldown for the active ability of wilden hunter").

                    define("wil_hunter_cooldown", 800);

            WIL_STALKER_COOLDOWN = builder.comment("cooldown for the active ability of wilden stalker").

                    define("wil_stalker_cooldown", 1300);

            WIXIE_COOLDOWN = builder.comment("cooldown for the active ability of wixie").

                    define("wixie_cooldown", 100);

            builder.pop();
        }
    }

    public static final Common COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    static {

        final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
    }

}
