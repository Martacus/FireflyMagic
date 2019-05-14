package com.mart.ffmagic.potion;

import com.mart.ffmagic.FireflyMagic;
import net.minecraft.potion.Potion;

public class PotionLiquidLuck extends PotionFireflyMagic {

    public PotionLiquidLuck() {
        super(false, 8171462, 3, 0);
        setRegistryName(FireflyMagic.MODID,"liquid_luck");
    }
}
