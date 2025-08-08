package com.mercifulrespawn;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = MercifulRespawnMod.MOD_ID)
public class RespawnManager {
    private static final Map<UUID, RespawnData> respawnDataMap = new HashMap<>();

    public static class RespawnData {
        public final Vec3 deathPos;
        public final long respawnTime;
        public boolean hasAttackedMob;

        public RespawnData(Vec3 deathPos) {
            this.deathPos = deathPos;
            this.respawnTime = System.currentTimeMillis();
            this.hasAttackedMob = false;
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            // 死亡地点を記録
            Vec3 deathPos = player.position();
            RespawnData respawnData = new RespawnData(deathPos);
            respawnDataMap.put(player.getUUID(), respawnData);

            // 復活地点を設定（死亡地点の近く）
            Vec3 respawnPos = calculateRespawnPosition(deathPos);
            serverPlayer.setPos(respawnPos.x, respawnPos.y, respawnPos.z);
        }
    }

    private static Vec3 calculateRespawnPosition(Vec3 deathPos) {
        // 設定から復活半径を取得
        int respawnRadius = Config.RESPAWN_RADIUS.get();
        
        // 死亡地点から設定された半径以内の安全な場所を探す
        double angle = Math.random() * 2 * Math.PI;
        double distance = Math.random() * respawnRadius;
        
        double x = deathPos.x + Math.cos(angle) * distance;
        double z = deathPos.z + Math.sin(angle) * distance;
        
        // Y座標は死亡地点と同じ高さを使用
        return new Vec3(x, deathPos.y, z);
    }

    public static RespawnData getRespawnData(UUID playerUUID) {
        return respawnDataMap.get(playerUUID);
    }

    public static void setHasAttackedMob(UUID playerUUID) {
        RespawnData data = respawnDataMap.get(playerUUID);
        if (data != null) {
            data.hasAttackedMob = true;
        }
    }

    public static boolean isInGracePeriod(UUID playerUUID) {
        // MODが無効の場合は常にfalse
        if (!Config.ENABLE_MOD.get()) {
            return false;
        }
        
        RespawnData data = respawnDataMap.get(playerUUID);
        if (data == null) return false;
        
        long currentTime = System.currentTimeMillis();
        long elapsedTicks = (currentTime - data.respawnTime) / 50; // 50ms = 1 tick
        
        // 設定から無敵時間を取得
        int gracePeriodTicks = Config.GRACE_PERIOD_TICKS.get();
        
        return elapsedTicks < gracePeriodTicks && !data.hasAttackedMob;
    }

    public static void removeRespawnData(UUID playerUUID) {
        respawnDataMap.remove(playerUUID);
    }
}
