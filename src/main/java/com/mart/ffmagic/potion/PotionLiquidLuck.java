package com.mart.ffmagic.potion;

import com.mart.ffmagic.FireflyMagic;
import net.minecraft.potion.Potion;

public class PotionLiquidLuck extends Potion {

    public PotionLiquidLuck() {
        super(false, 8171462);
        setRegistryName(FireflyMagic.MODID,"liquid_luck");
    }
}
