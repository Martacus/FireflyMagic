package com.mart.ffmagic;

import com.hrznstudio.titanium.particle.ParticleRegistry;
import com.hrznstudio.titanium.util.TitaniumMod;
import com.mart.ffmagic.block.ModBlocks;
import com.mart.ffmagic.entity.EntityFirefly;
import com.mart.ffmagic.item.ModItems;
import com.mart.ffmagic.particle.ParticleFirefly;
import com.mart.ffmagic.potion.*;
import com.mart.ffmagic.recipe.ModRecipes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mod("ffmagic")
public class FireflyMagic extends TitaniumMod
{
    public static EntityType<EntityFirefly> FIREFLY;

    public static Potion WISDOM_DRAUGHT;
    public static Potion LIQUID_LUCK;
    public static Potion MANDRAGORA;
    public static Potion DRUIDS_DELIGHT;
    public static Potion DRAGONS_WRATH;
    public static Potion BLOODFURY_POTION;

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

    @EventReceiver
    public static void registerPotion(final RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().register(WISDOM_DRAUGHT = new PotionWisdomDraught());
        event.getRegistry().register(LIQUID_LUCK = new PotionLiquidLuck());
        event.getRegistry().register(MANDRAGORA = new PotionMandragora());
        event.getRegistry().register(DRUIDS_DELIGHT = new PotionDruidsDelight());
        event.getRegistry().register(DRAGONS_WRATH = new PotionDragonsWrath());
        event.getRegistry().register(BLOODFURY_POTION = new PotionBloodfuryPotion());
    }


}
