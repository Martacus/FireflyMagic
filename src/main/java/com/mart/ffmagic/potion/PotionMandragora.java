package com.mart.ffmagic.potion;

import com.mart.ffmagic.FireflyMagic;
import net.minecraft.potion.Potion;

public class PotionMandragora extends PotionFireflyMagic {

    public PotionMandragora() {
        super(false, 8171462, 4, 0);
        setRegistryName(FireflyMagic.MODID,"mandragora");
    }
}
