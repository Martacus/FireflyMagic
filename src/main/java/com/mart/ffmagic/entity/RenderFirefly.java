package com.mart.ffmagic.entity;

import com.mart.ffmagic.FireflyMagic;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderFirefly extends RenderLiving<EntityFirefly> {


    public RenderFirefly(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new FireflyModel(), 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFirefly entity) {
        return new ResourceLocation(FireflyMagic.MODID, "");
    }
}
