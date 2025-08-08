package com.mercifulrespawn;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
    public static ForgeConfigSpec.IntValue RESPAWN_RADIUS;
    public static ForgeConfigSpec.IntValue GRACE_PERIOD_TICKS;
    public static ForgeConfigSpec.BooleanValue ENABLE_MOD;

    public static void register() {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        
        SERVER_BUILDER.comment("Merciful Respawn Configuration");

        ENABLE_MOD = SERVER_BUILDER
                .comment("MODを有効にするかどうか")
                .define("enableMod", true);

        RESPAWN_RADIUS = SERVER_BUILDER
                .comment("復活地点の半径（ブロック）")
                .defineInRange("respawnRadius", 50, 10, 200);

        GRACE_PERIOD_TICKS = SERVER_BUILDER
                .comment("無敵時間（tick）")
                .defineInRange("gracePeriodTicks", 6000, 1200, 12000);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }
}
