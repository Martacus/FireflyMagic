package com.mart.ffmagic.event;

import com.mart.ffmagic.FireflyMagic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

}
