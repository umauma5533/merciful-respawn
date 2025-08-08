package com.mercifulrespawn;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MercifulRespawnMod.MOD_ID)
public class MercifulRespawnMod {
    public static final String MOD_ID = "mercifulrespawn";

    public MercifulRespawnMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        
        // 設定を登録
        Config.register();
    }

    private void setup(final FMLCommonSetupEvent event) {
        // 初期化処理
        System.out.println("Merciful Respawn MOD が初期化されました");
    }
}
