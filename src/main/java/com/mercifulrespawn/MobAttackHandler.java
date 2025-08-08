package com.mercifulrespawn;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MercifulRespawnMod.MOD_ID)
public class MobAttackHandler {

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        Entity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();

        // プレイヤーがMOBに攻撃された場合
        if (target instanceof Player player && attacker instanceof Mob) {
            if (RespawnManager.isInGracePeriod(player.getUUID())) {
                // 無敵期間中は攻撃をキャンセル
                event.setCanceled(true);
            }
        }

        // プレイヤーがMOBを攻撃した場合
        if (attacker instanceof Player player && target instanceof Mob) {
            // プレイヤーがMOBを攻撃したことを記録
            RespawnManager.setHasAttackedMob(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();

        // プレイヤーがMOBにダメージを受けた場合
        if (target instanceof Player player && attacker instanceof Mob) {
            if (RespawnManager.isInGracePeriod(player.getUUID())) {
                // 無敵期間中はダメージをキャンセル
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        Entity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();

        // プレイヤーがMOBにダメージを受けた場合
        if (target instanceof Player player && attacker instanceof Mob) {
            if (RespawnManager.isInGracePeriod(player.getUUID())) {
                // 無敵期間中はダメージを0にする
                event.setAmount(0.0f);
            }
        }
    }
}
