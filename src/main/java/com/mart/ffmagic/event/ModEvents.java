package com.mart.ffmagic.event;

import com.mart.ffmagic.FireflyMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class ModEvents {

    @SubscribeEvent
    public static void onXPPickup(PlayerPickupXpEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        for(PotionEffect effect : player.getActivePotionEffects()){
            if(effect.getPotion() == FireflyMagic.WISDOM_DRAUGHT){
                event.getOrb().xpValue = event.getOrb().getXpValue() * 2;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerAttack(LivingDamageEvent event){
        Random random = new Random();
        Entity source = event.getSource().getTrueSource();
        if(source instanceof EntityPlayer && event.getEntity() instanceof EntityLivingBase){
            EntityPlayer player = (EntityPlayer) source;
            player.getActivePotionEffects().forEach(potionEffect -> {
                if(potionEffect.getPotion() == FireflyMagic.MANDRAGORA){
                    if(random.nextInt(2) == 0){
                        event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
                    }
                }
            });
        }
    }

}
