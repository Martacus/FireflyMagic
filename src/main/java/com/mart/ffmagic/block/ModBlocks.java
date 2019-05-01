package com.mart.ffmagic.block;

import com.hrznstudio.titanium.util.TitaniumMod;
import net.minecraft.block.Block;

public class ModBlocks {

    public static void registerBlocks(TitaniumMod mod){
        mod.addEntries(Block.class, BlockFireflyPress.BLOCK, BlockScrollWriter.BLOCK);
    }

}
