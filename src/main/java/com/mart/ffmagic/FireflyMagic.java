package com.mart.ffmagic;

import com.hrznstudio.titanium.particle.ParticleRegistry;
import com.hrznstudio.titanium.util.TitaniumMod;
import com.mart.ffmagic.block.ModBlocks;
import com.mart.ffmagic.entity.EntityFirefly;
import com.mart.ffmagic.item.ModItems;
import com.mart.ffmagic.particle.ParticleFirefly;
import com.mart.ffmagic.recipe.ModRecipes;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("ffmagic")
public class FireflyMagic extends TitaniumMod
{
    public static EntityType<EntityFirefly> FIREFLY;

    public static final String MODID = "ffmagic";

    public static final ItemGroup GROUP = new ItemGroup(12, "fireflymagic") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ModItems.firefly_jar);
        }
    };

    public FireflyMagic() {
        ModItems.registerItems(this);
        ModBlocks.registerBlocks(this);
        ModRecipes.addRecipes();
        ParticleRegistry.registerParticle(ParticleFirefly.class, new ResourceLocation("ffmagic:particle/particle_glow"));
    }

    @EventReceiver
    public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event)
    {
        FIREFLY = EntityType.Builder.create(EntityFirefly.class, EntityFirefly::new).tracker(128, 1, false).build(MODID + ":firefly");
        FIREFLY.setRegistryName(new ResourceLocation(MODID, "firefly"));

        event.getRegistry().register(FIREFLY);
    }

}
