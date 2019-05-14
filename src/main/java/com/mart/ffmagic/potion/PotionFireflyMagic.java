package com.mart.ffmagic.potion;

import com.mart.ffmagic.FireflyMagic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PotionFireflyMagic extends Potion {


    public static ResourceLocation texture = new ResourceLocation(FireflyMagic.MODID, "textures/effect/effect_bloodfury.png");
    final int textureX, textureY;


    protected PotionFireflyMagic(boolean isBadEffectIn, int liquidColorIn, int textureX, int textureY) {
        super(isBadEffectIn, liquidColorIn);
        this.textureX = textureX;
        this.textureY = textureY;
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderHUDEffect(PotionEffect effect, Gui gui, int x, int y, float z, float alpha) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        gui.drawTexturedModalRect(x + 3, y + 3, textureX * 18, textureY * 18, 18, 18);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderInventoryEffect(PotionEffect effect, Gui gui, int x, int y, float z) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        gui.drawTexturedModalRect(x +  6, y + 7, textureX * 18, textureY * 18, 18, 18);
    }
}
