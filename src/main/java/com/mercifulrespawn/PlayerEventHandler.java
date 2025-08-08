package com.mercifulrespawn;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MercifulRespawnMod.MOD_ID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player instanceof net.minecraft.server.level.ServerPlayer) {
                // プレイヤーがログインした時に復活データをクリア
                RespawnManager.removeRespawnData(player.getUUID());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof Player player) {
            // プレイヤーがログアウトした時に復活データをクリア
            RespawnManager.removeRespawnData(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof Player player) {
            // ディメンション変更時に復活データをクリア
            RespawnManager.removeRespawnData(player.getUUID());
        }
    }
}
