package com.mart.ffmagic.potion;

import com.mart.ffmagic.FireflyMagic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class PotionWisdomDraught extends Potion {

    public PotionWisdomDraught() {
        super(false, 8171462);
        setRegistryName(FireflyMagic.MODID,"wisdom_draught");
    }
}
